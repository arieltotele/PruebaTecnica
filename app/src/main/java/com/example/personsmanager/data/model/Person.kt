package com.example.personsmanager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Person(
    @PrimaryKey(autoGenerate = true)
    val idPerson: Int,
    val name: String,
    val lastName: String,
    val age:Int
):Serializable{
    constructor(name: String, lastName: String, age: Int):this(0,name, lastName, age)
}