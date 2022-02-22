package ru.fefu.activitytracker.extensions

import android.content.Context
import org.joda.time.DateTime
import org.joda.time.Duration
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.data.db.Activity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

fun Activity.getDistanceDisplayText(context: Context): String {
    val distance = when {
        points.size < 2 -> 0
        else -> {
            var sum = 0.0
            for (i in 1 until points.size) {
                sum += sqrt(
                    (points[i].first - points[i - 1].first).pow(2.0)
                            + (points[i].second - points[i - 1].second).pow(2.0)
                )
            }
            sum.toInt()
        }
    }

    return when {
        distance <= 1000 -> context.resources.getQuantityString(
            R.plurals.meters,
            distance,
            distance,
        )
        else -> context.getString(R.string.kilometers, distance / 1000.0)
    }
}

fun Activity.getEndDateDisplayText(context: Context): String {
    val duration = Duration(endDateTime, DateTime.now())
    val minutes = duration.standardMinutes.toInt()
    val hours = duration.standardHours.toInt()

    return when {
        minutes <= 60 -> "${
            context.resources.getQuantityString(
                R.plurals.minutes,
                minutes,
                minutes,
            )
        } ${context.getString(R.string.ago)}"

        hours <= 24 -> "${
            context.resources.getQuantityString(
                R.plurals.hours,
                hours,
                hours,
            )
        } ${context.getString(R.string.ago)}"

        else -> SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            .format(endDateTime.toDate())
    }
}

fun Activity.getDurationDisplayText(context: Context, useAbbreviations: Boolean = false): String {
    val duration = Duration(startDateTime, endDateTime)
    val minutesRemainder = (duration.standardMinutes % 60).toInt()
    val hoursRemainder = (duration.standardHours % 24).toInt()

    val hoursStringId: Int
    val minutesStringId: Int

    when {
        useAbbreviations -> {
            hoursStringId = R.plurals.abbreviated_hours
            minutesStringId = R.plurals.abbreviated_minutes
        }
        else -> {
            hoursStringId = R.plurals.hours
            minutesStringId = R.plurals.minutes
        }
    }

    return when {
        hoursRemainder != 0 && minutesRemainder != 0 -> "${
            context.resources.getQuantityString(hoursStringId, hoursRemainder, hoursRemainder)
        } ${
            context.resources.getQuantityString(minutesStringId, minutesRemainder, minutesRemainder)
        }"
        hoursRemainder == 1 -> context.resources.getQuantityString(minutesStringId, 60, 60)
        hoursRemainder > 1 -> context.resources.getQuantityString(
            hoursStringId,
            hoursRemainder,
            hoursRemainder,
        )
        else -> context.resources.getQuantityString(
            minutesStringId,
            minutesRemainder,
            minutesRemainder,
        )
    }
}


