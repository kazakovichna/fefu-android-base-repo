package ru.fefu.activitytracker.data.db

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity_types")
data class ActivityType(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "name_id")
    @StringRes
    val nameId: Int,

    @ColumnInfo(name = "image_id")
    @DrawableRes
    val imageId: Int,
)