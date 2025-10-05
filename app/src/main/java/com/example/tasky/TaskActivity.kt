package com.example.tasky

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tasky.databinding.ActivityTaskBinding
import java.util.*

class TaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTaskBinding
    private lateinit var adapter: TaskAdapter
    private val taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  Setup toolbar as ActionBar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Tasks"

        // Get profession from intent
        val profession = intent.getStringExtra("profession") ?: "User"

        // Load default tasks based on profession
        loadDefaultTasks(profession)

        adapter = TaskAdapter(taskList)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Date field → show Date + Time Picker (no past dates allowed)
        binding.etDate.setOnClickListener {
            val calendar = Calendar.getInstance()

            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->
                    // After choosing date, open time picker
                    TimePickerDialog(
                        this,
                        { _, hourOfDay, minute ->
                            val selectedDateTime = String.format(
                                "%02d/%02d/%04d %02d:%02d",
                                dayOfMonth, month + 1, year, hourOfDay, minute
                            )
                            binding.etDate.setText(selectedDateTime)
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    ).show()
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            // Disable past dates
            datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
            datePicker.show()
        }

        //  Add new task button
        binding.btnAddTask.setOnClickListener {
            val title = binding.etTask.text.toString().trim()
            val date = binding.etDate.text.toString().trim()
            if (title.isNotEmpty() && date.isNotEmpty()) {
                taskList.add(Task(title, date, false))
                adapter.notifyItemInserted(taskList.size - 1)
                binding.etTask.text.clear()
                binding.etDate.text.clear()
            } else {
                Toast.makeText(this, "Enter task title and date", Toast.LENGTH_SHORT).show()
            }
        }

        //  Go to Profile button
        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("name", "Test User")
            intent.putExtra("email", "test@example.com")
            intent.putExtra("profession", profession)
            startActivity(intent)
        }
    }

    //  Handle toolbar back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadDefaultTasks(profession: String) {
        when (profession) {
            "Doctor" -> {
                taskList.add(Task("Appointment with patients", "2025-08-27 10:00", false))
                taskList.add(Task("Medical conference", "2025-09-01 14:00", false))
                taskList.add(Task("Research paper writing", "2025-09-10 09:00", false))
            }
            "Student (School)" -> {
                taskList.add(Task("Math Exam", "2025-09-02 09:00", false))
                taskList.add(Task("Science Assignment", "2025-09-05 17:00", false))
                taskList.add(Task("Parents Meeting", "2025-09-07 15:00", false))
            }
            "Student (University)" -> {
                taskList.add(Task("Project Presentation", "2025-09-03 11:00", false))
                taskList.add(Task("Lab Report Submission", "2025-09-06 16:00", false))
                taskList.add(Task("Viva Exam", "2025-09-09 13:00", false))
            }
            "Teacher" -> {
                taskList.add(Task("Today subjects", "2025-08-28 08:00", false))
                taskList.add(Task("Paper Correction", "2025-08-30 18:00", false))
                taskList.add(Task("Principal Meeting", "2025-09-01 10:30", false))
            }
            "Engineer" -> {
                taskList.add(Task("Team Standup Meeting", "2025-08-28 09:00", false))
                taskList.add(Task("Submit Design Document", "2025-09-02 14:00", false))
                taskList.add(Task("Client Review", "2025-09-05 15:30", false))
            }
            "Lawyer" -> {
                taskList.add(Task("Court Hearing", "2025-08-29 11:00", false))
                taskList.add(Task("Case Preparation", "2025-09-03 18:00", false))
                taskList.add(Task("Client Consultation", "2025-09-07 12:00", false))
            }
            "Principal" -> {
                taskList.add(Task("School Inspection", "2025-08-30 09:30", false))
                taskList.add(Task("Teacher Review Meeting", "2025-09-04 10:00", false))
                taskList.add(Task("Parent Feedback Session", "2025-09-08 16:00", false))
            }
            else -> taskList.clear()
        }
    }
}
