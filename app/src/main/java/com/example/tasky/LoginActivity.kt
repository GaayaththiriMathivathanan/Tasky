package com.example.tasky

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tasky.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 1002  // unique request code for login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ✅ Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Normal Email/Password login
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, ProfessionActivity::class.java)
            intent.putExtra("email", email)
            startActivity(intent)
            finish()
        }

        //  Google Sign-In button
        binding.btnGoogleSignIn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        // ✅ Navigate to SignupActivity
        binding.tvSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        // ✅ Navigate to Reset Password
        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
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

                Toast.makeText(this, "Signed in as $name", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, ProfessionActivity::class.java)
                intent.putExtra("name", name)
                intent.putExtra("email", email)
                intent.putExtra("profession", "Student (University)") // default
                startActivity(intent)
                finish()

            } catch (e: ApiException) {
                Toast.makeText(this, "Google Sign-In Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
