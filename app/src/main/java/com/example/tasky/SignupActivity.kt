package com.example.tasky

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tasky.databinding.ActivitySignupBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 1001  // unique request code for signup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //  Setup professions in spinner
        val professions = listOf(
            "Doctor", "Engineer", "Teacher", "Lawyer",
            "Principal", "Student (School)", "Student (University)"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, professions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerProfession.adapter = adapter

        //  Show parent email only for School Students
        binding.spinnerProfession.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedProfession = professions[position]
                binding.parentEmailLayout.visibility =
                    if (selectedProfession == "Student (School)") View.VISIBLE else View.GONE
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        //  Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //  Handle Signup button
        binding.btnSignup.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()
            val profession = binding.spinnerProfession.selectedItem.toString()
            val parentEmail = binding.etParentEmail.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (profession == "Student (School)" && parentEmail.isEmpty()) {
                Toast.makeText(this, "Please enter parent email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Navigate to ProfessionActivity
            val intent = Intent(this, ProfessionActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("email", email)
            intent.putExtra("profession", profession)
            if (profession == "Student (School)") {
                intent.putExtra("parentEmail", parentEmail)
            }
            startActivity(intent)
            finish()
        }

        //  Already have account → Login
        binding.btnLoginHere.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        //  Google Sign-In button navigate mail
        binding.btnGoogleSignIn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    // ✅ Handle Google Sign-In Result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)

                val name = account.displayName ?: "Google User"
                val email = account.email ?: "No Email"

                Toast.makeText(this, "Signed up with $name", Toast.LENGTH_SHORT).show()

                //  After Google signup → go to ProfessionActivity
                val intent = Intent(this, ProfessionActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("email", email)
                intent.putExtra("profession", "Student (University)") // default if none selected
                startActivity(intent)
                finish()

            } catch (e: ApiException) {
                Toast.makeText(this, "Google Sign-In Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
