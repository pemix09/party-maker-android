package com.example.party_maker_android.ui.fragments

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.party_maker_android.R

class AddEventFragment : Fragment() {

    companion object {
        fun newInstance() = AddEventFragment()
    }
    private lateinit var fragmentContext: Context
    private lateinit var viewModel: AddEventViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_event, container, false)
    }

    override fun onAttach(activity: Activity) {
        fragmentContext = activity
        super.onAttach(activity)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AddEventViewModel::class.java).also {
            it.setContext(fragmentContext)
        }
    }

}