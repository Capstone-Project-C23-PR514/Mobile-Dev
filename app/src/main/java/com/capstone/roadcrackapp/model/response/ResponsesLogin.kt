package com.capstone.roadcrackapp.model.response

import com.google.gson.annotations.SerializedName

data class ResponsesLogin(

	@field:SerializedName("loginUsers")
	val loginUsers: LoginUsers,

	@field:SerializedName("message")
	val message: String
)

data class LoginUsers(

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String,

	@field:SerializedName("token")
	val token: String
)
