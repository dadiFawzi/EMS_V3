package com.example.ems_v3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ems_v3.model.Expense
import com.example.ems_v3.model.mission.Mission

@Dao
interface MissionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(mission: Mission)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(customers: List<Mission>)


    @Query("SELECT * FROM mission_table")
    fun loadAllMissions(): Array<Mission>
}
