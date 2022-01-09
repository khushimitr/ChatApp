package com.example.chatappui.data.remote

import com.example.chatappui.domain.model.Message

interface MessageService {

    suspend fun getAllMessages() : List<Message>

    companion object{
        // for emulator
//        const val BASE_URL = "http://10.0.2.2:8080"

        const val BASE_URL = "http://192.168.0.101:8080"

        // for running on local phone
//        ip ----> local ip for your computer
    }

    sealed class Endpoints(val url : String){
        object GetAllMessages : Endpoints("$BASE_URL/messages")
    }
}