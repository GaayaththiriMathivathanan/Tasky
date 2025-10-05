package com.example.tasky

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tasky.databinding.ActivityProfessionBinding

class ProfessionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfessionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra("name") ?: "Guest"

        binding.spinnerProfession.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>, view: View?, position: Int, id: Long) {
                val selected = parent.getItemAtPosition(position).toString()
                binding.etOtherProfession.visibility = if (selected == "Other") View.VISIBLE else View.GONE
            }

            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
        }

        binding.btnContinue.setOnClickListener {
            var selectedProfession = binding.spinnerProfession.selectedItem.toString()

            if (selectedProfession == "Other") {
                val other = binding.etOtherProfession.text.toString().trim()
                if (other.isNotEmpty()) {
                    selectedProfession = other
                } else {
                    Toast.makeText(this, "Please enter your profession", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            // ✅ Pass both name and profession
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("name", userName)
            intent.putExtra("profession", selectedProfession)
            startActivity(intent)
            finish()
        }
    }
}
