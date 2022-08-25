package com.example.tbc_course_22.extensions

import android.app.Application
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first


object DataStore {

    private val Application.store by preferencesDataStore(
        name = "test"
    )

    fun getPreferences(): Flow<Preferences> {
        return App.appContext.store.data
    }

    suspend fun save(key: String, value: String) {
        App.appContext.store.edit {
            it[stringPreferencesKey(key)] = value
        }
    }

    suspend fun clear() {
        App.appContext.store.edit {
            it.clear()
        }
    }


}

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        appContext = this

    }

    companion object {

        lateinit var appContext: Application

    }

}
