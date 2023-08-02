package com.example.ems_v3.model.mission

import androidx.room.Junction
import androidx.room.Relation
import com.example.ems_v3.model.Customer


data class MissionWithCustomers(
    @Relation(
        parentColumn = "id",
        entityColumn = "customerId",
        associateBy = Junction(MissionCustomerCrossRef::class)
    )
    val mission: Mission,
    @Relation(
        parentColumn = "id",
        entityColumn = "missionId",
        associateBy = Junction(MissionCustomerCrossRef::class)
    )
    val customers: List<Customer>
)