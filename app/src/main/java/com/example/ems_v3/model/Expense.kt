package com.example.ems_v3.model

import androidx.room.Entity
import androidx.room.PrimaryKey


 class Expense (

    val id: Long = 0,
    val amount : Double,
    val comment : String,
    val invoice_link:String

    )
/*
{
    constructor(expenseType: ExpenseType  ,ammount: Float,comment: String) : this(0, expenseType,ammount,comment)
}*/
