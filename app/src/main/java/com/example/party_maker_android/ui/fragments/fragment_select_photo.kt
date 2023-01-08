package com.example.party_maker_android.ui.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.party_maker_android.databinding.FragmentSelectPhotoBinding


class fragment_select_photo : Fragment() {
    private lateinit var binding: FragmentSelectPhotoBinding
    private val photoGalleryRequestCode: Int = 1
    private val pickPhotoRequestCode: Int = 2
    private val requestCameraAccessRequestCode: Int = 3
    private val makePhotoRequestCode: Int = 4
    var selectedPhoto: Uri? = null
    var selectedBitMap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonClickListener()
    }

    private fun setButtonClickListener(){
        binding.makePhotoButton.setOnClickListener {
            makePhoto()
        }
        binding.selectFromGalleryButton.setOnClickListener{
            selectFromGallery()
        }
    }

    private fun selectFromGallery() {
        activity?.let{
            if(ContextCompat.checkSelfPermission(it.applicationContext, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), photoGalleryRequestCode)
            }
            else{
                val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(pickPhoto, pickPhotoRequestCode)
            }
        }
    }

    private fun makePhoto(){
        activity?.let{
            if(ContextCompat.checkSelfPermission(it.applicationContext, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA), requestCameraAccessRequestCode)
            }
            else{
                val openCamera: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(openCamera, makePhotoRequestCode)
            }
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == photoGalleryRequestCode){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, pickPhotoRequestCode)
            }
        }
        else if(requestCode == requestCameraAccessRequestCode){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val openCamera: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(openCamera, makePhotoRequestCode)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //hide the buttons when photo was selected
        if(data != null){
            binding.selectPhotoText.isVisible = false
            binding.selectFromGalleryButton.isVisible = false
            binding.makePhotoButton.isVisible = false
        }
        //photo from galery
        if(requestCode == pickPhotoRequestCode && resultCode == Activity.RESULT_OK && data != null){
            selectedPhoto = data.data
            if(Build.VERSION.SDK_INT >= 28){
                val source = ImageDecoder.createSource(context?.contentResolver!!, selectedPhoto!!)
                selectedBitMap = ImageDecoder.decodeBitmap(source)
                binding.selectedPhotoImage.setImageBitmap(selectedBitMap)
            }
            else{
                selectedBitMap = MediaStore.Images.Media.getBitmap(context?.contentResolver!!, selectedPhoto)
                binding.selectedPhotoImage.setImageBitmap(selectedBitMap)
            }
        }
        //photo from camera
        else if(requestCode == makePhotoRequestCode && resultCode == Activity.RESULT_OK && data != null){
            selectedBitMap = data.extras?.get("data") as Bitmap
            binding.selectedPhotoImage.setImageBitmap(selectedBitMap)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}