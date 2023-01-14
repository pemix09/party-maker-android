package com.example.party_maker_android.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.party_maker_android.R
import com.example.party_maker_android.domain.models.EventEntity
import com.example.party_maker_android.domain.services.Base64Helper

class EventsAdapter(private val ctx: Context, private val events: List<EventEntity>): BaseAdapter() {

    private var inflater = LayoutInflater.from(ctx)

    override fun getCount(): Int {
        return events.size
    }

    override fun getItem(index: Int): Any {
        return events[index]
    }

    override fun getItemId(index: Int): Long {
        return events[index].id?.toLong()!!
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = inflater.inflate(R.layout.event_list_item, null)
        var eventName = view.findViewById<TextView>(R.id.eventName)
        var eventImage = view.findViewById<ImageView>(R.id.eventPhoto)
        eventName.text = events[position].name
        eventImage.setImageBitmap(events[position].photo?.let { Base64Helper.getBitmapFromBase64(it) })

        return view
    }
}