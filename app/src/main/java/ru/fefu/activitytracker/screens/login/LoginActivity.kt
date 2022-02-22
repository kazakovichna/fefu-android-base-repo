package ru.fefu.activitytracker.screens.login

import android.content.Intent
import android.os.Bundle
import ru.fefu.activitytracker.BackButtonToolbarActivity
import ru.fefu.activitytracker.databinding.ActivityLoginBinding
import ru.fefu.activitytracker.screens.main.MainActivity

class LoginActivity : BackButtonToolbarActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        setButtonsHandlers()
    }

    private fun setButtonsHandlers() {
        binding.continueButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}