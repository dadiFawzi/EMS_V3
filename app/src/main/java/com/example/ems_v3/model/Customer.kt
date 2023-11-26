package com.example.ems_v3.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


 class Customer(
    val id  : Long = 0,
    val name: String,
    val city: String,
    val distance: Double
)

{
@Ignore
constructor(name: String,city: String,distance: Double) : this(0, name,city,distance)
}
