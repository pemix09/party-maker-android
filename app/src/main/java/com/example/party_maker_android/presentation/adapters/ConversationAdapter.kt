package com.example.party_maker_android.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.party_maker_android.R
import com.example.party_maker_android.domain.models.Message
import com.example.party_maker_android.domain.services.UserService

class ConversationAdapter(private val ctx: Context, private val messages: List<Message>, private val currentUserId: String): BaseAdapter() {
    private var inflater = LayoutInflater.from(ctx)
    private val userService = UserService(ctx)

    override fun getCount(): Int {
        return messages.size
    }

    override fun getItem(index: Int): Message {
        return messages[index]
    }

    override fun getItemId(index: Int): Long {
        return messages[index].id!!
    }

    override fun getView(index: Int, convertView: View?, parent: ViewGroup?): View {
        var msg = messages[index]
        //current user - should be on the right
        if(msg.senderId == currentUserId){
            var currentUserMessage = inflater.inflate(R.layout.conversation_current_user_message, null)
            currentUserMessage.findViewById<TextView>(R.id.current_user_name).text = msg.senderId
            currentUserMessage.findViewById<TextView>(R.id.current_user_message_text).text = msg.content
            return currentUserMessage
        }
        //some other user message - on the left
        else{
            var otherUserMessage = inflater.inflate(R.layout.conversation_someone_message, null)
            otherUserMessage.findViewById<TextView>(R.id.other_user_sender_name).text = msg.senderId
            otherUserMessage.findViewById<TextView>(R.id.other_user_message_text).text = msg.content
            return otherUserMessage
        }
    }
}