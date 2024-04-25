package com.doma.domaapp

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textToSpeech = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale("pt", "BR"))
            }
        }

        val items = arrayOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        val listView = findViewById<ListView>(R.id.listView)
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val text = items[position]
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    override fun onDestroy() {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.shutdown()
        super.onDestroy()
    }
}