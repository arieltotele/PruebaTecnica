package com.example.personsmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.personsmanager.R
import com.example.personsmanager.data.model.relations.PersonWithAddresses
import kotlinx.android.synthetic.main.persom_detail_preview.view.*

class PersonsAdapter:RecyclerView.Adapter<PersonsAdapter.PersonViewHolder>() {

    inner class PersonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<PersonWithAddresses>(){
        override fun areItemsTheSame(
            oldItem: PersonWithAddresses,
            newItem: PersonWithAddresses
        ): Boolean {
            return oldItem.person.idPerson == newItem.person.idPerson
        }

        override fun areContentsTheSame(
            oldItem: PersonWithAddresses,
            newItem: PersonWithAddresses
        ): Boolean {
            return  oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        return PersonViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.persom_detail_preview,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val personWithAddresses = differ.currentList[position]
        val person = personWithAddresses.person
        holder.itemView.apply {
            tvName.text = person.name +" "+person.lastName
            tvAge.text = person.age.toString() + " Años"
            setOnClickListener{
                onItemClickListener?.let {it(personWithAddresses)}
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClickListener:((PersonWithAddresses) -> Unit)? = null

    fun setOnItemClickListener(listener: (PersonWithAddresses) -> Unit){
        onItemClickListener = listener
    }
}