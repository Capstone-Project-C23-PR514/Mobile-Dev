package com.capstone.roadcrackapp.model.network

import com.capstone.roadcrackapp.model.ResponseRegister
import com.capstone.roadcrackapp.model.response.ResponseLogin
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun postRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ):Response<ResponseRegister>

    @FormUrlEncoded
    @POST("login")
    suspend fun postLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ResponseLogin>
}