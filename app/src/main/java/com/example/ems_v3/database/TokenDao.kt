package com.example.ems_v3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.ems_v3.model.Token
import com.example.ems_v3.model.User

@Dao
interface TokenDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(token: Token)


    @Query("SELECT * FROM token_table")
    fun loadAllUsers(): Array<User>
    @Query("SELECT * FROM token_table WHERE id = :tokenId")
    fun getUserById(tokenId: Long): Token?
    @Update
    fun updateUser(user: User)

}