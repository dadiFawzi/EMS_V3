package com.example.ems_v3.Report

data class ExpenseItem(
    val expenseDate: String,
    val missionTitle: String,
    val expenseAmount: Double,
    val customerName: String,
    val expenseList: List<Pair<String, Double>>
)
