package com.example.ems_v3.model.mission

import androidx.room.Entity



@Entity(tableName = "mission_customer_cross_ref", primaryKeys = ["missionId", "customerId"])
data class MissionCustomerCrossRef(
    val missionId: Long,
    val customerId: Long
)