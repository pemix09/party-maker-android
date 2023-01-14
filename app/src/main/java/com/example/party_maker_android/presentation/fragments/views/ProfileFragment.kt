package com.example.party_maker_android.presentation.fragments.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.party_maker_android.R
import com.example.party_maker_android.domain.services.Base64Helper
import com.example.party_maker_android.databinding.FragmentProfileBinding
import com.example.party_maker_android.presentation.fragments.models.ProfileViewModel
import java.text.SimpleDateFormat

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.setContext(requireContext())
        setViewModelObservers()
    }

    private fun setViewModelObservers(){
        viewModel.errorMessage.observe(viewLifecycleOwner){
            val toast = Toast.makeText(context, it.toString(), Toast.LENGTH_LONG)
            toast.show()
        }
        viewModel.currentUser.observe(viewLifecycleOwner){
            binding.profileUserName.text = it?.userName
            if(it?.photo != null){
                binding.profileImage.setImageBitmap(Base64Helper.getBitmapFromBase64(it.photo!!))
            }
            binding.profileEmail.text = it?.email
            binding.profileRole.text = it?.role
            binding.ratingText.text = context?.getString(R.string.profile_rating_text, it?.avarageRating.toString())

            var dateFormat = SimpleDateFormat("MM.yyyy")
            var date = dateFormat.format(it?.registrationDate)
            binding.sinceText.text = context?.getString(R.string.registration_date_text, date)
        }
    }

}