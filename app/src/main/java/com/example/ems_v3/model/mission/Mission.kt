package com.example.ems_v3.model.mission

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


data  class Mission (
    val id : Long = 0 ,
    val mission_title : String ,
    val date: Date ,
    val missionComment : String,
    val idUser : Long ,
    val idExpense : Long ,
//TODO delete mission start and end date from views layout

        )
