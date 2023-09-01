package com.marfin_fadhilah.devtest.core.data.source.local

import com.marfin_fadhilah.devtest.core.data.source.local.entity.EmployeeEntity
import com.marfin_fadhilah.devtest.core.data.source.local.room.EmployeeDao
import kotlinx.coroutines.flow.Flow


class LocalDataSource constructor(private val employeeDao: EmployeeDao) {
    fun getAllEmployee(): Flow<List<EmployeeEntity>> = employeeDao.getAllEmployee()

    suspend fun insertEmployee(employeeList: List<EmployeeEntity>) =
        employeeDao.insertEmployee(employeeList)

    fun deleteEmployee(employee: EmployeeEntity) {
        employeeDao.deleteEmployee(employee)
    }

    fun updateEmployee(employee: EmployeeEntity) {
        employeeDao.updateEmployee(employee)
    }
}