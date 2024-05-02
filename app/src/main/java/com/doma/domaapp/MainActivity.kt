package com.doma.domaapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    val messagesButton = findViewById<ImageButton>(R.id.messages_button)
    val notificationsButton = findViewById<ImageButton>(R.id.notifications_button)

    messagesButton.setOnClickListener {
        val intent = Intent(this, MessagesActivity::class.java)
        startActivity(intent)
    }

    notificationsButton.setOnClickListener {
        val intent = Intent(this, NotificationsActivity::class.java)
        startActivity(intent)
    }
}
}