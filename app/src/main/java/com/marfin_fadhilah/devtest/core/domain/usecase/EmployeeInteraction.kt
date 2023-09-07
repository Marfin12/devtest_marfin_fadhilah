package com.marfin_fadhilah.devtest.core.domain.usecase

import com.marfin_fadhilah.devtest.core.domain.model.Employee
import com.marfin_fadhilah.devtest.core.domain.repository.IEmployeeRepository


class EmployeeInteraction(private val employeeRepository: IEmployeeRepository): EmployeeUseCase {
    override fun getAllEmployee() = employeeRepository.getAllEmployee()

    override suspend fun postEmployee(employee: Employee) = employeeRepository.postEmployee(employee)

    override fun deleteEmployee(employee: Employee) = employeeRepository.deleteEmployee(employee)

    override fun updateEmployee(employee: Employee) = employeeRepository.updateEmployee(employee)
}