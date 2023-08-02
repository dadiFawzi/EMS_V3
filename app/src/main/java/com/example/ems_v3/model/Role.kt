package com.example.ems_v3.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roles")
enum class Role {
    @PrimaryKey
    ADMIN,

    @PrimaryKey
    BACKOFFICE,

    @PrimaryKey
    USER
}