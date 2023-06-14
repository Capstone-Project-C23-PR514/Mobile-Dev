package com.capstone.roadcrackapp.model.response

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("users")
	val users: Users,

	@field:SerializedName("token")
	val token: String
)

data class Users(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
