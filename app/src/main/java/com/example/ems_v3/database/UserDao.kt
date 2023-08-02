package com.example.ems_v3.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ems_v3.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insertAll(users: List<User>)


    @Query("SELECT * FROM user_table")
    fun loadAllUsers(): Array<User>
}