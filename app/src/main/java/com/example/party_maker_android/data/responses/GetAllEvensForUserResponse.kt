package com.example.party_maker_android.data.responses

import com.example.party_maker_android.domain.models.EventEntity

class GetAllEvensForUserResponse {

    var followed: List<EventEntity>? = null
    var organized: List<EventEntity>? = null
}