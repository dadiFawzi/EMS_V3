package com.example.ems_v3.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "token_table")
data class Token (
    @PrimaryKey(autoGenerate = true)
    val id: Long ,
    var token : String
)