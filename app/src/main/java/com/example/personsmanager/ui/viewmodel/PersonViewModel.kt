package com.example.personsmanager.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personsmanager.data.db.PersonDatabase
import com.example.personsmanager.data.model.Address
import com.example.personsmanager.data.model.Person
import com.example.personsmanager.data.model.relations.PersonWithAddresses
import com.example.personsmanager.repository.PersonRepository
import com.example.personsmanager.util.Resource
import kotlinx.coroutines.launch

class PersonViewModel(val personRepository: PersonRepository): ViewModel() {

    val personsWithAddresses: MutableLiveData<Resource<List<PersonWithAddresses>>> = MutableLiveData()

    fun getPersonsWitAddresses() = viewModelScope.launch {
        personsWithAddresses.postValue(Resource.Loading())
        val response = personRepository.getPersonWithAddresses()
        if (!response.isNullOrEmpty()){
            personsWithAddresses.postValue(Resource.Success(response))
        }else{
            personsWithAddresses.postValue(
                Resource.Error("There are not persons registered."))
        }
    }

    suspend fun addPerson(person: Person) = viewModelScope.launch{
        val response = personRepository.upsertPerson(person)
    }

    suspend fun deletePerson(person: Person) = viewModelScope.launch{
        val response = personRepository.deletePerson(person)
    }

    suspend fun addAddress(address: Address) = viewModelScope.launch{
        val response = personRepository.upsertAddress(address)
    }

    suspend fun deleteAddress(address: Address) = viewModelScope.launch{
        val response = personRepository.deleteAddress(address)
    }

    suspend fun fulldata(){
        val persons = listOf(
            Person(1, "Ariel", "Herasme", 22),
            Person(2, "Asbel", "Perez", 20)
        )

        val addresses = listOf(
            Address(1, "Quinto Centenario", 1),
            Address(2, "sambil", 1),
            Address(3, "Agora", 2)
        )
            persons.forEach { personRepository.upsertPerson(it) }
            addresses.forEach { personRepository.upsertAddress(it) }

    }
}