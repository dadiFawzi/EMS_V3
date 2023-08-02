package com.example.ems_v3.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "customer_table")
data class Customer(
    @PrimaryKey(autoGenerate = true)
    val id  : Long,
    val name: String,
    val city: String,
    val distance: Double
)
