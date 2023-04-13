package com.example.cloud_ass3_realtime

data class Message(
    val text: String = "",
    val senderId: String = "",
    val receiverId :String = "",
    val timestamp: Long = 0
)
