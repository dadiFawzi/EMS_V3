package com.example.ems_v3.database

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ems_v3.model.User

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
     fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.ABORT)
     fun insertAll(users: List<User>)


    @Query("SELECT * FROM user_table")
    fun loadAllUsers(): Array<User>
    @Query("SELECT * FROM user_table WHERE id = :userId")
     fun getUserById(userId: Long): User?
    @Update
     fun updateUser(user: User)

}