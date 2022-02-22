package ru.fefu.activitytracker.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ActivityDao {
    @Query("SELECT * FROM activities")
    fun getAll(): LiveData<List<Activity>>

    @Query("SELECT * FROM activities WHERE username=:username")
    fun getByUser(username: String): LiveData<List<Activity>>

    @Query("SELECT * FROM activities WHERE username!=:username")
    fun getAllExceptUser(username: String): LiveData<List<Activity>>

    @Query("SELECT * FROM activities WHERE id=:id")
    fun getById(id: Int): Activity

    @Insert
    fun insertAll(vararg activities: Activity)

    @Delete
    fun delete(activity: Activity)

    @Transaction
    @Query("SELECT * FROM activities")
    fun getAllWithTypes(): LiveData<List<ActivityWithType>>

    @Transaction
    @Query("SELECT * FROM activities WHERE username=:username")
    fun getByUserWithTypes(username: String): LiveData<List<ActivityWithType>>

    @Transaction
    @Query("SELECT * FROM activities WHERE username!=:username")
    fun getAllExceptUserWithTypes(username: String): LiveData<List<ActivityWithType>>

    @Transaction
    @Query("SELECT * FROM activities WHERE id=:id")
    fun getByIdWithType(id: Int): ActivityWithType

}