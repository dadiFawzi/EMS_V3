package com.example.ems_v3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ems_v3.model.mission.Mission

@Dao
interface MissionDao {
  /*  @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(mission: Mission)

    @Query("SELECT * FROM mission_table")
    suspend fun getAllMission(): List<Mission>*/
}
