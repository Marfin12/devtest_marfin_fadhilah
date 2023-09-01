package com.marfin_fadhilah.devtest.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class EmployeeCallResponse(
    @field:SerializedName("status")
    val status: String = "",

    @field:SerializedName("message")
    val message: String = ""
)