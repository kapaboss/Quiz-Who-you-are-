package com.example.quiz

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.Chronometer
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var chronometer: Chronometer
    private lateinit var startBtn: Button
    private lateinit var stopBtn: Button

    private var running = false
    private var pauseOffset: Long = 0
    lateinit var timerTextView: TextView
    lateinit var mainLayout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        chronometer = findViewById<Chronometer>(R.id.myChronometer)
        startBtn = findViewById(R.id.startButton)
        stopBtn = findViewById(R.id.stopButton)

        timerTextView = findViewById(R.id.timerCounter)
        mainLayout = findViewById(R.id.main)

        val countDownTimer: CountDownTimer = object : CountDownTimer(45000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                timerTextView.text = "Pozostało $secondsLeft sekund"
            }

            override fun onFinish() {
                timerTextView.text = "Koniec czasu"
                timerTextView.setTextColor(Color.BLACK)
            }
        }

        val spinner: Spinner = findViewById(R.id.spinner)
        val colors = arrayOf("Czarny", "Niebieski", "Żółty", "Zielony")

        val adapter = ArrayAdapter( this,
            android.R.layout.simple_list_item_1,
            colors
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val radioGroup = findViewById<RadioGroup>(R.id.radioGroupPersonality)
        val myDatePickerCalendar: DatePicker = findViewById<DatePicker>(R.id.datePicker)
        val myTimePicker: TimePicker = findViewById<TimePicker>(R.id.timePicker)


        val checkBoxMusic: CheckBox = findViewById(R.id.checkboxMusic)
        val checkBoxSport: CheckBox = findViewById(R.id.checkboxSport)
        val checkBoxBooks: CheckBox = findViewById(R.id.checkboxBooks)
        val checkBoxMovies: CheckBox = findViewById(R.id.checkboxMovies)
        val seekBar: SeekBar = findViewById(R.id.seekBar)


        val button = findViewById<Button>(R.id.summaryButton)
        button.setOnClickListener {
            val selectedId = radioGroup.checkedRadioButtonId

            val radioButton = findViewById<RadioButton>(selectedId)
            val selectedText = radioButton.text.toString()


            val day = myDatePickerCalendar.dayOfMonth
            val month = myDatePickerCalendar.month
            val year = myDatePickerCalendar.year
            val selectedDate = "$day/${month + 1}/$year"


            val hour = myTimePicker.hour
            val minute = myTimePicker.minute
            val selectedTime = "$hour:$minute"


            val selectedInterests = mutableListOf<String>()
            if (checkBoxMusic.isChecked) selectedInterests.add("Muzyka")
            if (checkBoxSport.isChecked) selectedInterests.add("Sport")
            if (checkBoxBooks.isChecked) selectedInterests.add("Książki")
            if (checkBoxMovies.isChecked) selectedInterests.add("Filmy")
            val interestsString = selectedInterests.joinToString(", ")


            val timeWithFriends = seekBar.progress


            val selectedColor = spinner.selectedItem.toString()


            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("Radio_Button", selectedText)
            intent.putExtra("DatePicker", selectedDate)
            intent.putExtra("TimePicker", selectedTime)
            intent.putExtra("Interests", interestsString)
            intent.putExtra("TimeWithFriends", timeWithFriends)
            intent.putExtra("SelectedColor", selectedColor)
            intent.putExtra("TimeCheck", timerTextView.text)

            startActivity(intent)
        }


        startBtn.setOnClickListener {

            if (!running) {
                countDownTimer.start()
                chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
                chronometer.start()
                running = true
            }
        }

        stopBtn.setOnClickListener {
            if (running) {
                countDownTimer.cancel()
                pauseOffset = SystemClock.elapsedRealtime() - chronometer.base
                Log.i("offset", "pauseOffset: $pauseOffset")
                chronometer.stop()
                running = false
            }
        }


    }
}