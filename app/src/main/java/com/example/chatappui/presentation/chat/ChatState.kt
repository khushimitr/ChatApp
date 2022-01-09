package com.example.chatappui.presentation.chat

import com.example.chatappui.domain.model.Message

data class ChatState(
    val messages : List<Message> = emptyList(),
    val isLoading : Boolean = false,
)
