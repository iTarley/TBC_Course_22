package com.example.tbc_course_22.ui.main.authentication.home

import androidx.lifecycle.ViewModel
import com.example.tbc_course_22.extensions.DataStore

class HomeViewModel : ViewModel() {
    fun getPreferences() = DataStore.getPreferences()


    suspend fun clear() {
        DataStore.clear()
    }
}