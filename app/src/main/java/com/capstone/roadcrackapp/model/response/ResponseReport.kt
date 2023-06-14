package com.capstone.roadcrackapp.model.response

import com.google.gson.annotations.SerializedName

data class ResponseReport(

	@field:SerializedName("reports")
	val reports: List<ReportsItem>,

	@field:SerializedName("message")
	val message: String
)

data class ReportsItem(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("lokasi")
	val lokasi: String,

	@field:SerializedName("akurasi")
	val akurasi: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("judul")
	val judul: String,

	@field:SerializedName("gambar")
	val gambar: String,

	@field:SerializedName("desc")
	val desc: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
