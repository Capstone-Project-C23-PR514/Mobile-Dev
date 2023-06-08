package com.capstone.roadcrackapp.model

import com.google.gson.annotations.SerializedName

data class ResponseRegister(

	@field:SerializedName("metadata")
	val metadata: String,

	@field:SerializedName("registered")
	val registered: Registered,

	@field:SerializedName("status")
	val status: Int
)

data class Registered(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("nama_lengkap")
	val namaLengkap: String,

	@field:SerializedName("email")
	val email: String
)




