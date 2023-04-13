package com.example.cloud_ass3_realtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class Chat : AppCompatActivity() {

    private lateinit var messagesRecyclerView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button

    private lateinit var senderUid: String
    private lateinit var receiverUid: String
    private var messagesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("chat")

    private lateinit var messagesAdapter: MessageAdapter
    private val messagesList = mutableListOf<Message>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val user = intent.getStringExtra("user")
        messagesRecyclerView = findViewById(R.id.messages_recycler_view)
        messageEditText = findViewById(R.id.message_input)
        sendButton = findViewById(R.id.send_button)

        receiverUid = "8xYMJEZEaTcfzZskOI5uXS1mAOt1"
        senderUid = user.toString()

        messagesAdapter = MessageAdapter(this, messagesList, senderUid)
        messagesRecyclerView.layoutManager = LinearLayoutManager(this)
        messagesRecyclerView.adapter = messagesAdapter

        sendButton.setOnClickListener {
            val messageText = messageEditText.text.toString().trim()
            Log.e("Basma ",messageText)
            if (messageText.isNotEmpty()) {
                sendMessage(messageText)
                messageEditText.setText("")
            }
        }

        messagesRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val message = snapshot.getValue(Message::class.java)
                if (message != null) {
                    messagesList.add(message)
                    messagesAdapter.notifyItemInserted(messagesList.size - 1)
                    messagesRecyclerView.scrollToPosition(messagesList.size - 1)
                }
            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun sendMessage(messageText: String) {
        val timestamp = System.currentTimeMillis()
        val message = Message(messageText, senderUid, receiverUid, timestamp)
        Log.e("Basma ","1")
        messagesRef.push().setValue(message)
        Log.e("Basma ","2")
    }
}
