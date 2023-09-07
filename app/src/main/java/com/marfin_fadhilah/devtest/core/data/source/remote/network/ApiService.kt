package com.marfin_fadhilah.devtest.core.data.source.remote.network

import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeCallResponse
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeListResponse
import com.marfin_fadhilah.devtest.core.domain.model.Employee
import retrofit2.http.*


interface ApiService {
    @GET("employees")
    suspend fun getList(): EmployeeListResponse

    @FormUrlEncoded
    @POST("create")
    suspend fun postEmployee(
        @Field("name") name: String,
        @Field("salary") salary: String,
        @Field("age") age: String
    ): EmployeeCallResponse

    @DELETE("delete/{id}")
    suspend fun deleteEmployee(@Path("id") id: String): EmployeeCallResponse

    @PUT("put/{id}")
    suspend fun updateEmployee(
        @Path("id") id: String,
        @Body employee: Employee
    ): EmployeeCallResponse
}