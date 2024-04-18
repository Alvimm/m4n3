package com.doma.domaapp

//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat

//class MainActivity : AppCompatActivity() {
  //  override fun onCreate(savedInstanceState: Bundle?) {
    //    super.onCreate(savedInstanceState)
      //  enableEdgeToEdge()
        //setContentView(R.layout.activity_main)
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
          //  val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            //v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            //insets
        //}
    //}
//}

import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var audioHelper: AudioHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val items = arrayOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        val listView = findViewById<ListView>(R.id.listView)
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val text = items[position]
            if (audioHelper.audioOutputAvailable(AudioDeviceInfo.TYPE_BUILTIN_SPEAKER)) {
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
            }
        }

        textToSpeech = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                //textToSpeech.language = Locale.getDefault()
                textToSpeech.setLanguage(Locale("pt", "BR"))
            }
        }

        audioHelper = AudioHelper(this)
    }

    override fun onDestroy() {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.shutdown()
        super.onDestroy()
    }
    inner class AudioHelper(private val context: Context) {

        private val audioManager: AudioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        fun audioOutputAvailable(type: Int): Boolean {
            if (!context.packageManager.hasSystemFeature(PackageManager.FEATURE_AUDIO_OUTPUT)) {
                return false
            }

            return audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS).any { it.type == type }
        }
    }
}

