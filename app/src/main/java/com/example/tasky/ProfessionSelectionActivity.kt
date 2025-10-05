package com.example.tasky

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tasky.databinding.ActivityProfessionSelectionBinding

class ProfessionSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfessionSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfessionSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Student option toggle between school/university
        binding.radioGroupStudent.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radioSchool -> {
                    binding.etParentEmail.visibility = View.VISIBLE
                }
                R.id.radioUniversity -> {
                    binding.etParentEmail.visibility = View.GONE
                }
            }
        }

        binding.btnContinue.setOnClickListener {
            val selectedProfession = when (binding.radioGroupProfessions.checkedRadioButtonId) {
                R.id.radioDoctor -> "Doctor"
                R.id.radioTeacher -> "Teacher"
                R.id.radioLawyer -> "Lawyer"
                R.id.radioEngineer -> "Engineer"
                R.id.radioPrincipal -> "Principal"
                R.id.radioStudent -> {
                    if (binding.radioSchool.isChecked) {
                        if (binding.etParentEmail.text.toString().isEmpty()) {
                            Toast.makeText(this, "Parent email required for school students", Toast.LENGTH_SHORT).show()
                            return@setOnClickListener
                        }
                        "School Student"
                    } else {
                        "University Student"
                    }
                }
                R.id.radioOther -> {
                    val customProfession = binding.etOtherProfession.text.toString()
                    if (customProfession.isEmpty()) {
                        Toast.makeText(this, "Please enter your profession", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    customProfession
                }
                else -> {
                    Toast.makeText(this, "Please select a profession", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            // Pass profession to Task screen
            val intent = Intent(this, TaskActivity::class.java)
            intent.putExtra("profession", selectedProfession)
            startActivity(intent)
            finish()
        }
    }
}