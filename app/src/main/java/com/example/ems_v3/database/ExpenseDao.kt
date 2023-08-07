package com.example.ems_v3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ems_v3.model.Expense


@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpense(expense: Expense)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllExpense(expense: List<Expense>)

    @Query("SELECT * FROM expense_table")
    fun loadAllExpenses(): Array<Expense>

}