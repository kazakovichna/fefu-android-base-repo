package ru.fefu.activitytracker.screens.registration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import ru.fefu.activitytracker.BackButtonToolbarActivity
import ru.fefu.activitytracker.NoUnderlineClickableSpan
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.databinding.ActivityRegistrationBinding
import ru.fefu.activitytracker.extensions.getTagsIntervals
import ru.fefu.activitytracker.extensions.removeTags
import ru.fefu.activitytracker.screens.main.MainActivity

class RegistrationActivity : BackButtonToolbarActivity() {
    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar()
        setSpannable()
        setSexMenuAdapter()
        setSexMenuOnFocusChangeListener()
        setButtonsHandlers()
    }

    private fun setSpannable() {
        val rawString = getString(R.string.label_raw_confirmation)
        val intervals = rawString.getTagsIntervals()
        val string = rawString.removeTags()
        val spannable = SpannableString(string)

        for (interval in intervals) {
            spannable.setSpan(
                object : NoUnderlineClickableSpan() {
                    override fun onClick(p0: View) {
                        Toast.makeText(
                            this@RegistrationActivity,
                            string.substring(interval.first, interval.second),
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                },
                interval.first,
                interval.second,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
            )
        }

        binding.confirmationLabel.apply {
            text = spannable
            movementMethod = LinkMovementMethod.getInstance()
        }
    }

    private fun setSexMenuAdapter() {
        val items = listOf(
            getString(R.string.label_sex_man),
            getString(R.string.label_sex_woman),
            getString(R.string.label_sex_other),
        )
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        binding.sexMenuEditText.setAdapter(adapter)
    }

    private fun setSexMenuOnFocusChangeListener() {
        binding.sexMenuEditText.onFocusChangeListener = OnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
                binding.sexMenuEditText.showDropDown()
            }
        }
    }

    private fun setButtonsHandlers() {
        binding.continueButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}