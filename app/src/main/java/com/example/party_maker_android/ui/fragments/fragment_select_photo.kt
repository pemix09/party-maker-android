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
    var selectedPhoto: Uri? = null
    var selectedBitMap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

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

        }
        binding.selectFromGalleryButton.setOnClickListener{
            selectFromGallery(it)
        }
    }

    private fun selectFromGallery(view: View){
        activity?.let{
            if(ContextCompat.checkSelfPermission(it.applicationContext, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), photoGalleryRequestCode)
            }
            else{
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, pickPhotoRequestCode)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == photoGalleryRequestCode){
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(intent, pickPhotoRequestCode)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == pickPhotoRequestCode && resultCode == Activity.RESULT_OK && data != null){
            selectedPhoto = data.data
        }
        try{
            context?.let{
                if(selectedPhoto != null){
                    //hide the select photo buttons
                    binding.selectPhotoText.isVisible = false
                    binding.selectFromGalleryButton.isVisible = false
                    binding.makePhotoButton.isVisible = false

                    if(Build.VERSION.SDK_INT >= 28){
                        val source = ImageDecoder.createSource(it.contentResolver, selectedPhoto!!)
                        selectedBitMap = ImageDecoder.decodeBitmap(source)
                        binding.selectedPhotoImage.setImageBitmap(selectedBitMap)
                    }
                    else{
                        selectedBitMap = MediaStore.Images.Media.getBitmap(it.contentResolver, selectedPhoto)
                        binding.selectedPhotoImage.setImageBitmap(selectedBitMap)
                    }
                }
            }
        }catch (e: Exception){
            e.printStackTrace()
            binding.errorMessage.text = e.message
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}