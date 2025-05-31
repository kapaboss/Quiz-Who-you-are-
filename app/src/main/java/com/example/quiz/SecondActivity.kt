package com.example.quiz

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_second)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val summaryTextView = findViewById<TextView>(R.id.personality_textView)
        val message = intent.getStringExtra("Radio_Button")
        summaryTextView.text = if (message.isNullOrEmpty()) {
            "Nie wybrano"
        } else {
            "Typ osoby: " + message
        }

        val quizDateTextView = findViewById<TextView>(R.id.quiz_date)
        val message2 = intent.getStringExtra("DatePicker")
        quizDateTextView.text = if (message2.isNullOrEmpty()){
            "Nie wybrano"
        }
        else{
            "Data wykonania: " + message2
        }
    }


}




