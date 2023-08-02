package com.example.ems_v3.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ems_v3.model.User
import com.example.ems_v3.model.mission.Mission

@Database(entities = [User::class,Mission::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun missionDao() : MissionDao



}
