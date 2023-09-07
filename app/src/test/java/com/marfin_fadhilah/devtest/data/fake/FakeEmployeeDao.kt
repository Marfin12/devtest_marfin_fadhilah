package com.marfin_fadhilah.devtest.data.fake

import com.marfin_fadhilah.devtest.core.data.source.local.entity.EmployeeEntity
import com.marfin_fadhilah.devtest.core.data.source.local.room.EmployeeDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeEmployeeDao : EmployeeDao {

    var employeeEntity = mutableListOf<EmployeeEntity>()

    override fun getAllEmployee(): Flow<List<EmployeeEntity>> {
        return flowOf(employeeEntity.toList())
    }

    override suspend fun insertEmployee(employee: List<EmployeeEntity>) {
        employeeEntity.addAll(employee)
    }

    override fun deleteEmployee(employee: EmployeeEntity) {
        employeeEntity.remove(employee)
    }

    override fun updateEmployee(employee: EmployeeEntity) {
        employeeEntity[employee.employeeId] = employee
    }
}