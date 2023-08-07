package com.example.ems_v3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ems_v3.model.Expense


@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(expense: Expense)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(expense: List<Expense>)

 /*   @Query("SELECT * FROM expense_table")
    fun loadAllExpenses(): Array<Expense>

*/
}