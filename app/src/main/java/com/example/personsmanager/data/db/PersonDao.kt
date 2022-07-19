package com.example.personsmanager.data.db

import androidx.room.Dao
import androidx.room.*
import com.example.personsmanager.data.model.Person
import com.example.personsmanager.data.model.Address
import com.example.personsmanager.data.model.relations.PersonWithAddresses
import androidx.lifecycle.LiveData

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPerson(person: Person): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAddress(address: Address): Long

    @Delete
    suspend fun deletePerson(person: Person)

    @Delete
    suspend fun deleteAddress(address: Address)

    @Transaction
    @Query("SELECT * FROM Person")
    suspend fun getPersonsWithAddresses(): List<PersonWithAddresses>

}