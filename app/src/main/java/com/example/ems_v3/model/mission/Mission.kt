package com.example.ems_v3.model.mission

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity(tableName = "mission")
data  class Mission (
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0 ,
    val missionTitle : String ,
    val date: Date ,
    val missionComment : String,
    val idUser : Long ,
    val idExpense : Long ,
//TODO delete mission start and end date from views layout

        )
