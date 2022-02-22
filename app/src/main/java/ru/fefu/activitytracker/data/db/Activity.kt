package ru.fefu.activitytracker.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime

@Entity(tableName = "activities")
data class Activity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "type_id")
    val typeId: Int,

    @ColumnInfo(name = "start_date_time")
    val startDateTime: DateTime,

    @ColumnInfo(name = "end_date_time")
    val endDateTime: DateTime,

    @ColumnInfo(name = "points")
    val points: List<Pair<Double, Double>>,

    @ColumnInfo(name = "username")
    val username: String,

    @ColumnInfo(name = "comment")
    val comment: String? = null,
)

