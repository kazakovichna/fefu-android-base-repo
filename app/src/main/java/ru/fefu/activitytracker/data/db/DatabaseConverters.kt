package ru.fefu.activitytracker.data.db

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.joda.time.DateTime

class DatabaseConverters {
    @TypeConverter
    fun pointsToString(value: List<Pair<Double, Double>>) = Json.encodeToString(value)

    @TypeConverter
    fun pointsFromString(value: String) = Json.decodeFromString<List<Pair<Double, Double>>>(value)

    @TypeConverter
    fun dateToString(date: DateTime) = date.toString()

    @TypeConverter
    fun dateFromString(date: String): DateTime = DateTime.parse(date)
}