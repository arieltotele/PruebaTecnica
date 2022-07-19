package com.example.personsmanager.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.personsmanager.R
import com.example.personsmanager.data.db.PersonDatabase
import com.example.personsmanager.data.model.Address
import com.example.personsmanager.data.model.Person
import com.example.personsmanager.databinding.FragmentSavedPersonsBinding
import com.example.personsmanager.repository.PersonRepository
import com.example.personsmanager.ui.viewmodel.PersonViewModel
import com.example.personsmanager.ui.viewmodel.PersonViewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_formulary.*
import kotlinx.coroutines.launch

class FormularyFragment:Fragment(R.layout.fragment_formulary) {
    private lateinit var viewModel: PersonViewModel
    private lateinit var binding: FragmentSavedPersonsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = PersonRepository(PersonDatabase(requireContext()))
        val viewModelProvider = PersonViewModelProvider(repository)
        viewModel = ViewModelProvider(this, viewModelProvider).get(PersonViewModel::class.java)

        btnSend.setOnClickListener {
            if (!(tvfrmName.text.isNullOrBlank() && tvfrmLastname.text.isNullOrEmpty()
                        &&tvfrmAge.text.isNullOrEmpty()) ){
                            val person = Person( tvfrmName.text.toString(),
                                tvfrmLastname.text.toString(),
                                Integer.parseInt(tvfrmAge.getText().toString()))
                val addresses = listOf(
                    Address("Quinto Centenario", person.idPerson),
                    Address( "sambil", person.idPerson),
                    Address( "Agora", person.idPerson)
                )
                lifecycleScope.launch {
                    viewModel.addPerson(person)
                    addresses.forEach { viewModel.addAddress(it) }
                }

                Snackbar.make(view, "Person added succesfully", Snackbar.LENGTH_LONG).show()

            }
        }
    }
}