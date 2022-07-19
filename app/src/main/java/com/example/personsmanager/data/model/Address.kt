package com.example.personsmanager.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Address(
    @PrimaryKey(autoGenerate = true) val idAddress: Int,
    val addressDetail: String,
    val personOwnId: Int
): Serializable{
    constructor(addressDetail: String, personOwnId: Int):this(0, addressDetail, personOwnId)
}