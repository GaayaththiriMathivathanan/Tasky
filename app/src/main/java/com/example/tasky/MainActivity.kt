package com.example.tasky

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.tasky.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val profession = intent.getStringExtra("profession") ?: "User"
        val name = intent.getStringExtra("name") ?: "Guest"

        // Drawer toggle
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // ✅ Update nav header
        val headerView = binding.navigationView.getHeaderView(0)
        val tvUserName = headerView.findViewById<android.widget.TextView>(R.id.tvUserName)
        val tvUserProfession = headerView.findViewById<android.widget.TextView>(R.id.tvUserProfession)
        tvUserName.text = name
        tvUserProfession.text = profession

        // Navigation item clicks
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_tasks -> {
                    val intent = Intent(this, TaskActivity::class.java)
                    intent.putExtra("profession", profession)
                    startActivity(intent)
                }
                R.id.nav_profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("profession", profession)
                    intent.putExtra("name", name)
                    startActivity(intent)
                }
                R.id.nav_logout -> {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }

        // Dashboard buttons
        binding.btnTasks.setOnClickListener {
            val intent = Intent(this, TaskActivity::class.java)
            intent.putExtra("profession", profession)
            startActivity(intent)
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("profession", profession)
            intent.putExtra("name", name)
            startActivity(intent)
        }
    }
}
