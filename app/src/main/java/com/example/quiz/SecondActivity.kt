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

        val personality = intent.getStringExtra("Radio_Button") ?: ""
        val interests = intent.getStringExtra("Interests") ?: ""
        val timeWithFriends = intent.getIntExtra("TimeWithFriends", -1)
        val color = intent.getStringExtra("SelectedColor") ?: ""

        val descriptionBuilder = StringBuilder()


        if (personality.isNotEmpty()) {
            descriptionBuilder.append("Jesteś  $personality")
        }


        if (interests.isNotEmpty()) {
            descriptionBuilder.append(", lubiący $interests")
        }


        if (timeWithFriends in 0..10) {
            if (timeWithFriends < 5) {
                descriptionBuilder.append(", wolisz spędzać czas samotnie")
            } else {
                descriptionBuilder.append(",  lubisz towarzystwo")
            }
        }


        if (color.isNotEmpty()) {
            val colorDescription = when (color) {
                "Czarny", "Niebieski" -> "osobą spokojną"
                "Żółty", "Zielony" -> "osobą energiczną"
                else -> ""
            }
            if (colorDescription.isNotEmpty()) {
                descriptionBuilder.append(", jesteś też $colorDescription")
            }
        }

        descriptionBuilder.append(".")

        val summaryTextView = findViewById<TextView>(R.id.personality_textView)
        summaryTextView.text = descriptionBuilder.toString()


        findViewById<TextView>(R.id.quiz_date).text =
            "Data wykonania: " + (intent.getStringExtra("DatePicker") ?: "Nie wybrano")
        findViewById<TextView>(R.id.quiz_time).text =
            "Godzina wykonania: " + (intent.getStringExtra("TimePicker") ?: "Nie wybrano")
        findViewById<TextView>(R.id.quiz_interests).text =
            "Zainteresowania: " + (interests.ifEmpty { "Nie wybrano" })
        findViewById<TextView>(R.id.quiz_friends_time).text =
            if (timeWithFriends == -1) "Czas ze znajomymi: Nie wybrano" else "Czas ze znajomymi: $timeWithFriends/10"
        findViewById<TextView>(R.id.quiz_color).text =
            "Wybrany kolor: " + (color.ifEmpty { "Nie wybrano" })
    }
}
