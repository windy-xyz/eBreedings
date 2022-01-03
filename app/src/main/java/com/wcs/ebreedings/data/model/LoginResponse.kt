package com.wcs.ebreedings.data.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("result") val result: String?,
    @SerializedName("errdesc") val errDesc: String?,
    @SerializedName("Master_data_Palm") val masterDataPalm: List<Palm>?,
    @SerializedName("CurrentMaster") val currentMaster : Int?
)
