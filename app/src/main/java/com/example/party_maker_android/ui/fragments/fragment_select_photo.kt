package com.example.party_maker_android.ui.fragments

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.party_maker_android.R
import com.example.party_maker_android.databinding.FragmentAddEventBinding
import com.example.party_maker_android.databinding.FragmentSelectPhotoBinding


class fragment_select_photo : Fragment() {
    var selectedPhoto: Uri? = null
    var selectedBitMap: Bitmap? = null

    private lateinit var binding: FragmentSelectPhotoBinding

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

    private fun selectFromGallery(){

    }
}