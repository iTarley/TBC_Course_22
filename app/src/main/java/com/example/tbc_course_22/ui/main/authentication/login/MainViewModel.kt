package com.example.tbc_course_22.ui.main

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbc_course_22.extensions.Resource
import com.example.tbc_course_22.models.login.Login
import com.example.tbc_course_22.models.login.LoginModel
import com.example.tbc_course_22.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<Resource<Login>>(Resource.Loading(true))
    val loginState = _loginState.asStateFlow()


    fun logIn(email: String,password: String) {
        viewModelScope.launch {
            loginResponse(email = email, password = password).collect{
                _loginState.value = it
            }
        }
    }

    private fun loginResponse(email: String,password: String) = flow<Resource<Login>> {
        emit(Resource.Loading(true))
        try {
            val response = RetrofitClient.getInformation().getLogin(LoginModel(email = email,password = password))
            if(response.isSuccessful){
                val body = response.body()

                Log.d("response", "login: ${body?.token}")
                emit(Resource.Success(body!!))
            }
            else{
                val error = response.errorBody()?.string()
                Log.d("responseER", "login: $error")
                emit(Resource.Error(error!!))

            }

        }catch (e:Throwable){
            emit(Resource.Error(e.toString()))
        }


    }
}