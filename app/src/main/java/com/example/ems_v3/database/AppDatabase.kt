package com.example.ems_v3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ems_v3.model.Customer
import com.example.ems_v3.model.Expense
import com.example.ems_v3.model.User
import com.example.ems_v3.model.mission.Mission
import com.example.ems_v3.model.mission.MissionCustomerCrossRef
import com.example.ems_v3.model.mission.MissionWithCustomers

@Database(entities = [User::class,Mission::class,Customer::class,Expense::class,MissionCustomerCrossRef::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun customerDao(): CustomerDao
    abstract fun missionDao() : MissionDao

    abstract  fun expenseDao() : ExpenseDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}
