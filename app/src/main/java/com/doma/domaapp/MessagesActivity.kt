package com.doma.domaapp

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MessagesActivity : AppCompatActivity() {


    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_messages)

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

            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_layout, null)
            textToSpeech.speak("Gostaria de ouvir a mensagem de $name?", TextToSpeech.QUEUE_FLUSH, null, "")
            val dialogMessage = dialogView.findViewById<TextView>(R.id.dialog_message)
            val positiveButton = dialogView.findViewById<Button>(R.id.dialog_positive_button)
            val negativeButton = dialogView.findViewById<Button>(R.id.dialog_negative_button)

            dialogMessage.text = "Gostaria de ouvir a mensagem de $name?"

            val alertDialog = AlertDialog.Builder(this).apply {
                setView(dialogView)
                create()
            }.show()

            positiveButton.setOnClickListener {
                textToSpeech.speak("Ouvindo a mensagem de $name: $message", TextToSpeech.QUEUE_FLUSH, null, "")
                alertDialog.dismiss()
            }
            negativeButton.setOnClickListener {
                alertDialog.dismiss()
            }
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