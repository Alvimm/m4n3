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

        val messages = mapOf(
            "Filipe" to "Reunião às 10 horas amanhã",
            "João" to "Entrega do projeto na sexta-feira",
            "José" to "Me liga depois",
            "Gabriel" to "PR para revisar",
            "Pedro" to "Fechar contrato com a empresa X"
        )
        val listView = findViewById<ListView>(R.id.listView)
        val adapter = ArrayAdapter(this, R.layout.list_item, messages.keys.toList())
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val name = messages.keys.elementAt(position)
            val message = messages[name]
            textToSpeech.speak("Ouvindo a mensagem de $name: $message", TextToSpeech.QUEUE_FLUSH, null, "")
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