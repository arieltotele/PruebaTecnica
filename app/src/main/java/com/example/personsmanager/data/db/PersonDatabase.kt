package com.example.personsmanager.data.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.personsmanager.data.model.Address
import com.example.personsmanager.data.model.Person

@Database(
    entities = [
        Person::class,
        Address::class
    ],
    version = 1
)
abstract class PersonDatabase:RoomDatabase() {
    abstract fun getArticleDao(): PersonDao

    companion object {
        @Volatile
        private var instance: PersonDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PersonDatabase::class.java,
                "person_db.db"
            ).allowMainThreadQueries().build()
    }
}