package ru.fefu.activitytracker

import android.text.TextPaint
import android.text.style.ClickableSpan

abstract class NoUnderlineClickableSpan : ClickableSpan() {
    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = false
    }
}