package com.example.ems_v3.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_types")
enum class ExpenseType {

    @PrimaryKey
    hotel,
    @PrimaryKey
    fuel ,
    @PrimaryKey
    taxi ,
    @PrimaryKey
    rentCar,
    @PrimaryKey
    restaurent,
    @PrimaryKey
    other

}