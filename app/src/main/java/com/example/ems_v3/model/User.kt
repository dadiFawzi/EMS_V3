package com.example.ems_v3.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val user_id: Long = 0,
    val username: String,
    val email: String,
    val password: String,
    var photo_link : String,
    var  jobTitle : String ,
    var phone : String ,
    val role : String

/*    private Long user_id;
private String username;
private String email;
private String  phone;
private String photo_link;
private String jobTitle;
private String password;

private String role ;*/



)
