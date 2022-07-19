package com.example.personsmanager.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.personsmanager.repository.PersonRepository

class PersonViewModelProvider(val personRepository: PersonRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PersonViewModel(personRepository) as T
    }
}