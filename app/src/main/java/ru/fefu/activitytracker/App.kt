package ru.fefu.activitytracker

import android.app.Application
import androidx.room.Room
import ru.fefu.activitytracker.data.db.AppDatabase
import ru.fefu.activitytracker.data.db.rdc

class App : Application() {
    companion object {
        lateinit var INSTANCE: App
    }

    val db: AppDatabase by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "app_database"
        ).allowMainThreadQueries().addCallback(rdc).build()
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

}