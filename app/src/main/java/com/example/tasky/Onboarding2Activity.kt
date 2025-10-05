package com.example.tasky

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tasky.databinding.ActivityOnboarding2Binding

class Onboarding2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboarding2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboarding2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, Onboarding3Activity::class.java))
            finish()
        }

        binding.btnSkip.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}