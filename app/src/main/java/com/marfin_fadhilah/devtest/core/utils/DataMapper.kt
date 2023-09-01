package com.marfin_fadhilah.devtest.core.utils

import com.marfin_fadhilah.devtest.core.data.source.local.entity.EmployeeEntity
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeResponse
import com.marfin_fadhilah.devtest.core.domain.model.Employee

object DataMapper {
    fun mapResponsesToEntities(input: List<EmployeeResponse>): List<EmployeeEntity> {
        val employeeList = ArrayList<EmployeeEntity>()
        input.map {
            val employee = EmployeeEntity(
                employeeId = it.id,
                employeeName = it.employee_name,
                employeeSalary = it.employee_salary,
                employeeAge = it.employee_age
            )
            employeeList.add(employee)
        }
        return employeeList
    }

    fun mapEntitiesToDomain(input: List<EmployeeEntity>): List<Employee> =
        input.map {
            Employee(
                id = it.employeeId,
                name = it.employeeName,
                salary = it.employeeSalary,
                age = it.employeeAge
            )
        }

    fun mapDomainToEntity(input: Employee) = EmployeeEntity(
        employeeId = input.id ?: -1,
        employeeName = input.name,
        employeeSalary = input.salary,
        employeeAge = input.age,
    )
}