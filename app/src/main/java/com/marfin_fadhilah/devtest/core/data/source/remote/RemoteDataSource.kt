package com.marfin_fadhilah.devtest.core.data.source.remote

import android.util.Log
import com.marfin_fadhilah.devtest.core.data.source.remote.network.ApiResponse
import com.marfin_fadhilah.devtest.core.data.source.remote.network.ApiService
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeCallResponse
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeResponse
import com.marfin_fadhilah.devtest.core.domain.model.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class RemoteDataSource constructor(private val apiService: ApiService) {
    suspend fun getAllEmployee(): Flow<ApiResponse<List<EmployeeResponse>>> {
        return flow {
            try {
                val response = apiService.getList()
                val dataArray = response.data
                if (dataArray.isNotEmpty()){
                    emit(ApiResponse.Success(response.data))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun postEmployee(employee: Employee): Flow<ApiResponse<EmployeeCallResponse>> {
        return flow {
            try {
                val response = apiService.postEmployee(
                    employee.name,
                    employee.age.toString(),
                    employee.salary.toString()
                )
                val status = response.status
                if (status.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                }
                else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun deleteEmployee(id: String): Flow<ApiResponse<EmployeeCallResponse>> {
        return flow {
            try {
                val response = apiService.deleteEmployee(id)
                val status = response.status
                if (status.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                }
                else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun updateEmployee(employee: Employee): Flow<ApiResponse<EmployeeCallResponse>> {
        return flow {
            try {
                val response = apiService.updateEmployee(
                    employee.id.toString(),
                    employee.name,
                    employee.salary.toString(),
                    employee.age.toString()
                )
                val status = response.status
                if (status.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                }
                else {
                    emit(ApiResponse.Empty)
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}