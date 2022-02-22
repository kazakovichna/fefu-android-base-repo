package ru.fefu.activitytracker.data.db

import android.content.ContentValues
import androidx.room.Database
import androidx.room.OnConflictStrategy
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.constants.ActivityTypes

@Database(
    entities = [
        Activity::class,
        ActivityType::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun activityDao(): ActivityDao

    abstract fun activityTypeDao(): ActivityTypeDao
}

val rdc: RoomDatabase.Callback = object : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        db.insert("activity_types", OnConflictStrategy.IGNORE, ContentValues().apply {
            put("id", ActivityTypes.BICYCLE)
            put("name_id", R.string.label_bicycle)
            put("image_id", R.drawable.activity_type_bicycle)
        })

        db.insert("activity_types", OnConflictStrategy.IGNORE, ContentValues().apply {
            put("id", ActivityTypes.RUN)
            put("name_id", R.string.label_run)
            put("image_id", R.drawable.activity_type_bicycle)
        })

        db.insert("activity_types", OnConflictStrategy.IGNORE, ContentValues().apply {
            put("id", ActivityTypes.WALK)
            put("name_id", R.string.label_walk)
            put("image_id", R.drawable.activity_type_bicycle)
        })
    }
}
