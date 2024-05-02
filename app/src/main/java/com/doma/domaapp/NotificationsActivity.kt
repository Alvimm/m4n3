package com.doma.domaapp

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class NotificationsActivity : AppCompatActivity() {
    private lateinit var textToSpeech: TextToSpeech
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_notifications)

        textToSpeech = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.setLanguage(Locale("pt", "BR"))
            }
        }

        val notifications = mapOf(
            "Alerta - Clima" to "Previsão de temporal hoje",
            "Alerta - Casa" to "Problema com a fiação elétrica",
            "Alerta - Segurança" to "Roubo na vizinhança",
            "Alerta - Trânsito" to "Acidente na avenida principal",
            "Alerta - Educação" to "Suspensão das aulas presenciais",
            "Alerta - Saúde" to "Vacinação contra a COVID-19 disponível"
        )
        val listView = findViewById<ListView>(R.id.listNotificationView)
        val adapter = ArrayAdapter(this, R.layout.list_item_notification, notifications.keys.toList())
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val alerta = notifications.keys.elementAt(position)
            val message = notifications[alerta]
            textToSpeech.speak(
                "$alerta: $message",
                TextToSpeech.QUEUE_FLUSH,
                null,
                ""
            )
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






