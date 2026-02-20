package com.example.waterandgasdevliveryappmvp.model.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int od;

    public String firstname;
    public String lastname;
    public String email;
    public long phone_number;
    public String password;

    public User(String firstname, String lastname, String email, long phone_number, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phone_number = phone_number;
        this.password = password;
    }
}
