package com.example.tbc_course_22.network

import com.example.tbc_course_22.models.login.Login
import com.example.tbc_course_22.models.login.LoginModel
import com.example.tbc_course_22.models.register.Register
import com.example.tbc_course_22.models.register.RegisterModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object RetrofitClient {
    private const val BASE_URL = "https://reqres.in/api/"

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val retrofitBuilder by lazy{
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    fun getInformation(): Authorization  = retrofitBuilder.create(Authorization::class.java)
}
interface Authorization{
    @POST("login")
    suspend fun getLogin(@Body userData: LoginModel): Response<Login>

    @POST("register")
    suspend fun getRegister(@Body userData: RegisterModel): Response<Register>
}