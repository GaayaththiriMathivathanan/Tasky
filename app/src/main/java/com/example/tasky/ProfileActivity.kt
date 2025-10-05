package com.example.tasky

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tasky.databinding.ActivityProfileBinding
import java.util.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private var profession: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Setup toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Profile"

        // ✅ Get data from MainActivity / SignupActivity
        val name = intent.getStringExtra("name") ?: ""
        val email = intent.getStringExtra("email") ?: ""
        profession = intent.getStringExtra("profession") ?: ""

        // Pre-fill fields
        binding.etName.setText(name)
        binding.etEmail.setText(email)

        // ✅ Date picker
        binding.etDob.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    binding.etDob.setText("$day/${month + 1}/$year")
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // ✅ Save changes → send back updated name + profession to MainActivity
        binding.btnSaveChanges.setOnClickListener {
            val finalName = binding.etName.text.toString().trim()
            val finalEmail = binding.etEmail.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val dob = binding.etDob.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (finalName.isEmpty() || finalEmail.isEmpty() || phone.isEmpty() || dob.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Profile Updated!", Toast.LENGTH_SHORT).show()

            // ✅ Go back to MainActivity with updated values
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("name", finalName)
            intent.putExtra("profession", profession)
            startActivity(intent)
            finish()
        }

        // ✅ Back button
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
