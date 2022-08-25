package com.example.tbc_course_22.extensions

import android.app.Application
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class DataStore(private val application: Application) {

    companion object{
        private val Application.store by preferencesDataStore(
            name = "settings",
        )


    }

    suspend fun saveState(key: String, value: String) {
        val prefKey = stringPreferencesKey(key)
        application.store.edit {
            it[prefKey] = value
            it[prefKey] = value
        }
    }


        //flow
    suspend fun checkState(key: String): String? {
        val text =  application.store.data.first()
        return text[stringPreferencesKey(key)]
    }
}
