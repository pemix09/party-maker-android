package com.example.party_maker_android.domain.models

import com.google.gson.annotations.SerializedName
import java.util.Date

class EventEntity {

    @SerializedName("id")
    var id: Int? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("date")
    var date: Date? = null

    @SerializedName("place")
    var place: String? = null

    @SerializedName("organizerId")
    var organizaerId: String? = null

    @SerializedName("participatorsIds")
    var participatorsIds: List<String>? = null

    @SerializedName("passId")
    var passId: Int? = null

    @SerializedName("photo")
    var photo: String? = null

    @SerializedName("musicGenreId")
    var musicGenreId: Int? = null

    @SerializedName("type")
    var type: String? = null

    @SerializedName("latitude")
    var latitude: Double? = null

    @SerializedName("longitude")
    var longitude: Double? = null

    @SerializedName("lastMessage")
    var lastMessage: String? = null

    @SerializedName("lastMessageTime")
    var lastMessageDate: Date? = null
}