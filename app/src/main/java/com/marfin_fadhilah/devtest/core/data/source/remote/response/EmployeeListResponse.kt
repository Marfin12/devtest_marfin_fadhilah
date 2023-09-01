package com.marfin_fadhilah.devtest.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class EmployeeListResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("data")
    val data: List<EmployeeResponse>
)