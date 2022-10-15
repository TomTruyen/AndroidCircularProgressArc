package com.tomtruyen.circularprogressarc.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.tomtruyen.circularprogressarc.CircularProgressArc

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        findViewById<CircularProgressArc>(R.id.arc)?.let {
            it.setProgress(50)
            it.setGradientColors(
                intArrayOf(
                    ContextCompat.getColor(this, R.color.teal_200),
                    ContextCompat.getColor(this, R.color.teal_700)
                )
            )
        }
    }
}