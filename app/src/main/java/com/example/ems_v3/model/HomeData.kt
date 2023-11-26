package com.example.ems_v3.model

import com.example.ems_v3.model.mission.Mission
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@NoArgsConstructor
@AllArgsConstructor
class HomeData(
    val user: User,
    val client: Customer,
    val mission: Mission,
    val expense: Expense,
   val expenseType: ExpenseType
    
)

