package com.capstone.roadcrackapp.model.response

import com.google.gson.annotations.SerializedName


data class ResponseUpload(

	@field:SerializedName("lokasi")
	val lokasi: String? = null,

	@field:SerializedName("akurasi")
	val akurasi: Int? = null,

	@field:SerializedName("judul")
	val judul: String? = null,

	@field:SerializedName("gambar")
	val gambar: String? = null,

	@field:SerializedName("desc")
	val desc: String? = null
)
