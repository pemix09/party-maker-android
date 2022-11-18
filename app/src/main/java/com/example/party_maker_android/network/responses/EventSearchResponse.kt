package com.example.party_maker_android.network.responses

import com.example.party_maker_android.network.model.EventEntity
import com.google.gson.annotations.SerializedName

class EventSearchResponse {

    @SerializedName("events")
    var events: List<EventEntity>? = null
}