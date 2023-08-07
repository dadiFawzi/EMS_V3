package com.example.ems_v3.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName ="expense_table")
data class Expense (

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val expenseType : ExpenseType ,
    val ammount : Float,
    val comment : String,

    )
/*
{
    constructor(expenseType: ExpenseType  ,ammount: Float,comment: String) : this(0, expenseType,ammount,comment)
}*/
