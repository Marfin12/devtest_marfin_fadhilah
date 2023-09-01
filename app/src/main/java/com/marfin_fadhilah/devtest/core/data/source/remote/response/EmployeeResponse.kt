package com.marfin_fadhilah.devtest.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class EmployeeResponse(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("employee_name")
    val employee_name: String,

    @field:SerializedName("employee_salary")
    val employee_salary: Int,

    @field:SerializedName("employee_age")
    val employee_age: Int,
)