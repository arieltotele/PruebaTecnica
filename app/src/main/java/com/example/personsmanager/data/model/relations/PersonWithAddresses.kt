package com.example.personsmanager.data.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.personsmanager.data.model.Address
import com.example.personsmanager.data.model.Person
import java.io.Serializable

data class PersonWithAddresses(
    @Embedded val person: Person,
    @Relation(
        parentColumn = "idPerson",
        entityColumn = "personOwnId"
    )

    val addresses: List<Address>
):Serializable