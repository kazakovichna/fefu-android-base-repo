package ru.fefu.activitytracker.data.db

import androidx.room.Embedded
import androidx.room.Relation

data class ActivityWithType(
    @Embedded
    val activity: Activity,

    @Relation(
        parentColumn = "type_id",
        entityColumn = "id"
    )
    val activityType: ActivityType
)