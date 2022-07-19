package com.example.personsmanager.repository

import com.example.personsmanager.data.db.PersonDatabase
import com.example.personsmanager.data.model.Address
import com.example.personsmanager.data.model.Person

class PersonRepository(private val db: PersonDatabase) {

    suspend fun getPersonWithAddresses() = db.getArticleDao().getPersonsWithAddresses()

    suspend fun upsertPerson(person: Person) = db.getArticleDao().upsertPerson(person)

    suspend fun upsertAddress(address: Address) = db.getArticleDao().upsertAddress(address)

    suspend fun deletePerson(person: Person) = db.getArticleDao().deletePerson(person)

    suspend fun deleteAddress(address: Address) = db.getArticleDao().deleteAddress(address)
}