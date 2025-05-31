package com.example.quiz

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.Chronometer
import android.widget.LinearLayout
import android.widget.TextView
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

        val button = findViewById<Button>(R.id.summaryButton)
        button.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java)
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