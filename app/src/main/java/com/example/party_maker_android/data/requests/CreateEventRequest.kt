package com.example.party_maker_android.data.requests

import com.google.gson.annotations.SerializedName
import java.time.OffsetDateTime
import java.util.*

class CreateEventRequest {
    var name: String? = null
    var description: String? = null
    var date: String? = null
    var place: String? = null
    var passId: Int? = null
    var photo: String? = null
    var musicGenreId: Int? = null
    var type: String? = null
    var latitude: Double? = null
    var longitude: Double? = null
}