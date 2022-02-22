package ru.fefu.activitytracker.screens.welcome

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import ru.fefu.activitytracker.databinding.ActivityWelcomeBinding
import ru.fefu.activitytracker.screens.login.LoginActivity
import ru.fefu.activitytracker.screens.registration.RegistrationActivity

class WelcomeActivity : Activity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setButtonsCallbacks()
    }

    private fun setButtonsCallbacks() {
        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}