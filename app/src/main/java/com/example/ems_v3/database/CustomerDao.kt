package com.example.ems_v3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ems_v3.model.Customer
import org.jetbrains.annotations.NotNull

@Dao
interface  CustomerDao{


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomer(customer: Customer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCustomer(customers: List<Customer>)

    @Query("SELECT * FROM customer_table")
    fun loadAllCustomers(): Array<Customer>
}