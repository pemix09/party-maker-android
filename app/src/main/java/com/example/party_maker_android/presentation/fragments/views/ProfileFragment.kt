package com.example.party_maker_android.presentation.fragments.views

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.party_maker_android.R
import com.example.party_maker_android.domain.services.Base64Helper
import com.example.party_maker_android.databinding.FragmentProfileBinding
import com.example.party_maker_android.presentation.adapters.EventsAdapter
import com.example.party_maker_android.presentation.fragments.viewModels.ProfileViewModel
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val TAG = "ProfileFragment"
    private lateinit var viewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        setViewObservers()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        viewModel.setContext(requireContext())
        setViewModelObservers()
        viewModel.fetchData()
    }

    private fun setViewObservers(){
        binding.eventsToReviewCard.setOnClickListener {
            var eventsToReviewFragment = EventListFragment.newInstance()
            var args = Bundle()
            args.putInt("Type", R.id.events_to_review_card)
            eventsToReviewFragment.arguments = args
            setEventsContainerContent(eventsToReviewFragment)
            binding.eventTitleLayout.visibility = LinearLayout.VISIBLE
            binding.eventsListTitle.text = "Events to review:"
        }
        binding.goBackButton.setOnClickListener {
            setEventsContainerContent(null)
            showAllCards()
        }
        binding.logoutPlaceholder.setOnClickListener {
            viewModel.logout()
        }
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

    private fun setEventsContainerContent(fragmentToAdd: Fragment?){
        if(fragmentToAdd == null){
            binding.profileEventsContainer.visibility = FrameLayout.INVISIBLE
        }
        else{
            var transaction = parentFragmentManager.beginTransaction()
            transaction.replace(binding.profileEventsContainer.id, fragmentToAdd)
            transaction.commit()
            hideAllCards()
        }
    }
    private fun hideAllCards(){
        binding.eventsToReviewCard.visibility = MaterialCardView.INVISIBLE
        binding.yourEventsCard.visibility = MaterialCardView.INVISIBLE
        binding.participatedEventsCard.visibility = MaterialCardView.INVISIBLE
        binding.followedEventsCard.visibility = MaterialCardView.INVISIBLE
        binding.eventTitleLayout.visibility = LinearLayout.VISIBLE
    }

    private fun showAllCards(){
        binding.eventsToReviewCard.visibility = MaterialCardView.VISIBLE
        binding.yourEventsCard.visibility = MaterialCardView.VISIBLE
        binding.participatedEventsCard.visibility = MaterialCardView.VISIBLE
        binding.followedEventsCard.visibility = MaterialCardView.VISIBLE
        binding.eventTitleLayout.visibility = LinearLayout.INVISIBLE
    }

}