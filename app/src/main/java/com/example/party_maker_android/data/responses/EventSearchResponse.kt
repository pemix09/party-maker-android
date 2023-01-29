package com.example.party_maker_android.data.responses

import com.example.party_maker_android.domain.models.EventEntity
import com.google.gson.annotations.SerializedName

class EventSearchResponse {

    @SerializedName("events")
    var events: List<EventEntity>? = null
}