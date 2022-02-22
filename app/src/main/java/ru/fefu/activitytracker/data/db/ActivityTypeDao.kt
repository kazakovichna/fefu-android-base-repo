package ru.fefu.activitytracker.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ActivityTypeDao {
    @Query("SELECT * FROM activity_types")
    fun getAll(): LiveData<List<ActivityType>>

    @Query("SELECT * FROM activity_types WHERE id=:id")
    fun getById(id: Int): ActivityType

    @Insert
    fun insertAll(vararg types: ActivityType)

    @Delete
    fun delete(type: ActivityType)
}