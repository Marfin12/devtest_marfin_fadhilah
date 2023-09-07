package com.marfin_fadhilah.devtest.core.data

import com.marfin_fadhilah.devtest.core.data.source.local.LocalDataSource
import com.marfin_fadhilah.devtest.core.data.source.remote.RemoteDataSource
import com.marfin_fadhilah.devtest.core.data.source.remote.network.ApiResponse
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeCallResponse
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeResponse
import com.marfin_fadhilah.devtest.core.domain.model.Employee
import com.marfin_fadhilah.devtest.core.domain.repository.IEmployeeRepository
import com.marfin_fadhilah.devtest.core.utils.DataMapper
import kotlinx.coroutines.flow.*
import java.util.concurrent.Executors


class EmployeeRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IEmployeeRepository {

    override fun getAllEmployee(): Flow<Resource<List<Employee>>> =
        object : NetworkBoundResource<List<Employee>, List<EmployeeResponse>>() {
            override fun loadFromDB(): Flow<List<Employee>> {
                return localDataSource.getAllEmployee().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Employee>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<EmployeeResponse>>> =
                remoteDataSource.getAllEmployee()

            override suspend fun saveCallResult(data: List<EmployeeResponse>) {
                val employeeList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertEmployee(employeeList)
            }
        }.asFlow()

    override suspend fun postEmployee(employee: Employee): Flow<Resource<EmployeeCallResponse>> =
        remoteDataSource.postEmployee(employee)

    override fun deleteEmployee(employee: Employee): Flow<Resource<EmployeeCallResponse>> =
        object : NetworkBoundResource<EmployeeCallResponse, EmployeeCallResponse>() {

            override fun loadFromDB(): Flow<EmployeeCallResponse> {
                return getSaveCallResult ?: flowOf(EmployeeCallResponse())
            }

            override fun shouldFetch(data: EmployeeCallResponse?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<EmployeeCallResponse>> =
                remoteDataSource.deleteEmployee(employee.id.toString())

            override suspend fun saveCallResult(data: EmployeeCallResponse) {
                val employeeEntity = DataMapper.mapDomainToEntity(employee)
                Executors.newSingleThreadExecutor().execute {
                    localDataSource.deleteEmployee(employeeEntity)
                }
            }
        }.asFlow()

    override fun updateEmployee(employee: Employee): Flow<Resource<EmployeeCallResponse>> =
        object : NetworkBoundResource<EmployeeCallResponse, EmployeeCallResponse>() {

            override fun loadFromDB(): Flow<EmployeeCallResponse> {
                return getSaveCallResult ?: flowOf(EmployeeCallResponse())
            }

            override fun shouldFetch(data: EmployeeCallResponse?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<EmployeeCallResponse>> =
                remoteDataSource.updateEmployee(employee)

            override suspend fun saveCallResult(data: EmployeeCallResponse) {
                val employeeEntity = DataMapper.mapDomainToEntity(employee)
                Executors.newSingleThreadExecutor().execute {
                    localDataSource.updateEmployee(employeeEntity)
                }
            }
        }.asFlow()
}
