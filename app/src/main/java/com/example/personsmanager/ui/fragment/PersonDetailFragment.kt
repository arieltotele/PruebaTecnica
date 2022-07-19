package com.example.personsmanager.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.personsmanager.R
import com.example.personsmanager.data.db.PersonDatabase
import com.example.personsmanager.repository.PersonRepository
import com.example.personsmanager.ui.viewmodel.PersonViewModel
import com.example.personsmanager.ui.viewmodel.PersonViewModelProvider
import kotlinx.android.synthetic.main.fragment_person_detail.*

class PersonDetailFragment:Fragment(R.layout.fragment_person_detail) {

    private lateinit var viewModel: PersonViewModel
    val args: PersonDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = PersonRepository(PersonDatabase(requireContext()))
        val viewModelProvider = PersonViewModelProvider(repository)
        viewModel = ViewModelProvider(this, viewModelProvider).get(PersonViewModel::class.java)

        val person = args.personWithAddres.person
        val addressesDetails = args.personWithAddres.addresses
        val detailAddressesText = addressesDetails.forEach{it.addressDetail}

        detailTvName.text = detailTvName.text.toString() + person.name +" "+person.lastName
        detailTvAge.text = detailTvAge.text.toString()+" "+person.age.toString()
        detailTvDirections.text = detailTvDirections.text.toString() +" "+
                "\n"+addressesDetails
    }
}