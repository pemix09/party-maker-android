package com.example.party_maker_android.domain.models

import java.util.Date

class Message {

    var id: Long? = null
    var senderId: String? = null
    var date: Date? = null
    var eventId: Int? = null
    var content: String? = null
    var read: Boolean? = null
    var senderName: String? = null
}