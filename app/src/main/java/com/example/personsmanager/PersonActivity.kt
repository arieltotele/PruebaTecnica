package com.example.personsmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.personsmanager.data.db.PersonDatabase
import com.example.personsmanager.data.model.Address
import com.example.personsmanager.data.model.Person
import com.example.personsmanager.databinding.ActivityPersonBinding
import com.example.personsmanager.repository.PersonRepository
import com.example.personsmanager.ui.viewmodel.PersonViewModel
import com.example.personsmanager.ui.viewmodel.PersonViewModelProvider
import kotlinx.android.synthetic.main.activity_person.*
import kotlinx.coroutines.launch

class PersonActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPersonBinding
    private lateinit var viewModel: PersonViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val repository = PersonRepository(PersonDatabase(this))
        val viewModelProvider = PersonViewModelProvider(repository)
        viewModel = ViewModelProvider(this, viewModelProvider).get(PersonViewModel::class.java)
        binding.bottomNavigationView.setupWithNavController(personsNavHostFragment.findNavController())
        val dao = PersonDatabase.invoke(this).getArticleDao()


    }
}