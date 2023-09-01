package com.marfin_fadhilah.devtest.core.domain.usecase

import com.marfin_fadhilah.devtest.core.data.Resource
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeCallResponse
import com.marfin_fadhilah.devtest.core.domain.model.Employee
import kotlinx.coroutines.flow.Flow


interface EmployeeUseCase {
    fun getAllEmployee(): Flow<Resource<List<Employee>>>
    fun postEmployee(employee: Employee): Flow<Resource<EmployeeCallResponse>>
    fun deleteEmployee(employee: Employee): Flow<Resource<EmployeeCallResponse>>
    fun updateEmployee(employee: Employee): Flow<Resource<EmployeeCallResponse>>
}