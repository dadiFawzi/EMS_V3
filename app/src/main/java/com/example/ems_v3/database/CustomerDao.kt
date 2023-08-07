package com.example.ems_v3.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ems_v3.model.Customer

@Dao
interface  CustomerDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(customer: Customer)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(customers: List<Customer>)

  /*  @Query("SELECT * FROM customer_table")
    fun loadAllCustomers(): Array<Customer>*/
}