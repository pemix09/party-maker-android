package com.example.party_maker_android.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.party_maker_android.R
import com.example.party_maker_android.domain.models.UserEntity
import com.example.party_maker_android.domain.services.Base64Helper

class ParticipantsAdapter(private val context: Context, private val participants: List<UserEntity>) : BaseAdapter() {
    private var inflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return participants.size
    }

    override fun getItem(index: Int): Any {
        return participants[index]
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = inflater.inflate(R.layout.participator_item_list, null)
        var participant = participants[position]
        if(participant.photo != null){
            view.findViewById<ImageView>(R.id.participant_photo!!)
                .setImageBitmap(Base64Helper.getBitmapFromBase64(participant.photo!!))
        }
        else{
            view.findViewById<ImageView>(R.id.participant_photo!!)
                .setImageResource(R.drawable.profile_icon)
        }
        view.findViewById<TextView>(R.id.participant_name!!)
            .text = participant.userName

        return view
    }
}