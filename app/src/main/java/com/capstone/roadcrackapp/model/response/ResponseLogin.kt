package com.capstone.roadcrackapp.model.response

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

	@field:SerializedName("metadata")
	val metadata: String,

	@field:SerializedName("users")
	val users: Users,

	@field:SerializedName("status")
	val status: Int,

	@field:SerializedName("token")
	val token: String
)

data class Users(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String,

	@field:SerializedName("email")
	val email: String
)
