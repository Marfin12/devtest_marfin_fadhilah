package com.marfin_fadhilah.devtest.data.fake

import com.marfin_fadhilah.devtest.DataDummy.dummyAge
import com.marfin_fadhilah.devtest.DataDummy.dummyId
import com.marfin_fadhilah.devtest.DataDummy.dummyName
import com.marfin_fadhilah.devtest.DataDummy.dummySalary
import com.marfin_fadhilah.devtest.core.data.source.remote.network.ApiService
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeCallResponse
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeListResponse
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeResponse
import com.marfin_fadhilah.devtest.core.domain.model.Employee

class FakeApiService : ApiService {
    override suspend fun getList(): EmployeeListResponse =
        EmployeeListResponse(
            "success",
            listOf(EmployeeResponse(dummyId, dummyName, dummySalary, dummyAge))
        )

    override suspend fun postEmployee(
        name: String,
        salary: String,
        age: String
    ): EmployeeCallResponse = EmployeeCallResponse(
        "success",
        "some message..."
    )

    override suspend fun deleteEmployee(id: String) = EmployeeCallResponse(
        "success",
        "some message..."
    )

    override suspend fun updateEmployee(id: String, employee: Employee) = EmployeeCallResponse(
        "success",
        "some message..."
    )
}