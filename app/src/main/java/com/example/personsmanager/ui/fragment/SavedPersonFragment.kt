package com.example.personsmanager.ui.fragment

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.personsmanager.R
import com.example.personsmanager.adapters.PersonsAdapter
import com.example.personsmanager.data.db.PersonDatabase
import com.example.personsmanager.data.model.Address
import com.example.personsmanager.data.model.Person
import com.example.personsmanager.databinding.FragmentSavedPersonsBinding
import com.example.personsmanager.repository.PersonRepository
import com.example.personsmanager.ui.viewmodel.PersonViewModel
import com.example.personsmanager.ui.viewmodel.PersonViewModelProvider
import com.example.personsmanager.util.Resource
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_saved_persons.*
import kotlinx.coroutines.launch

class SavedPersonFragment:Fragment(R.layout.fragment_saved_persons) {

    lateinit var viewModel: PersonViewModel
    private lateinit var personAdapter: PersonsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = PersonRepository(PersonDatabase(requireActivity()))
        val viewModelProvider = PersonViewModelProvider(repository)
        viewModel = ViewModelProvider(this, viewModelProvider).get(PersonViewModel::class.java)

        viewModel.getPersonsWitAddresses()

        setupRecyclerview()

//        lifecycleScope.launch { viewModel.fulldata()}

        personAdapter.setOnItemClickListener {
            val personWithAddresses = it
            val action = SavedPersonFragmentDirections.actionSavedPersonFragmentToPersonDetailFragment(personWithAddresses)
            findNavController().navigate(action)
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val person =  personAdapter.differ.currentList[position].person
                val address =  personAdapter.differ.currentList[position].addresses
                lifecycleScope.launch {
                    viewModel.deletePerson(person)
                    address.forEach { viewModel.deleteAddress(it) }
                }

                Snackbar.make(view, "Person deleted successfully", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        lifecycleScope.launch {
                            viewModel.addPerson(person)
                            address.forEach { viewModel.addAddress(it) }
                            personAdapter.notifyDataSetChanged()
                        }
                    }.show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvPersonsSaved)
        }

        viewModel.personsWithAddresses.observe(viewLifecycleOwner, Observer{ response->
            when(response){
                is Resource.Success ->{
                    hideProgressBar()
                    response.data?.let { personsResponse ->
                        personAdapter.differ.submitList(personsResponse)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.i("ERROR:", message)
                    }
                }

                is Resource.Loading -> showProgressBar()
            }
        })
    }

    private fun hideEmergencyText() {
        rvPersonsSaved.visibility = View.INVISIBLE
    }

    private fun hideProgressBar() {
        prgBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        prgBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerview(){
            personAdapter = PersonsAdapter()
            //TODO: Use ViewBing
            rvPersonsSaved.apply {
                adapter = personAdapter
                layoutManager = LinearLayoutManager(activity)

            }

    }
}