package com.marfin_fadhilah.devtest.utils

import com.marfin_fadhilah.devtest.core.data.source.local.entity.EmployeeEntity
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeResponse
import com.marfin_fadhilah.devtest.core.domain.model.Employee
import com.marfin_fadhilah.devtest.core.utils.DataMapper
import org.junit.Assert.assertEquals
import org.junit.Test

class DataMapperTest {

    @Test
    fun `mapResponsesToEntities should correctly map EmployeeResponse list to EmployeeEntity list`() {
        // Arrange: Create a list of EmployeeResponse objects
        val employeeResponses = listOf(
            EmployeeResponse(1, "John", 50000, 30),
            EmployeeResponse(2, "Alice", 60000, 28)
        )

        // Act: Call the mapping function
        val result = DataMapper.mapResponsesToEntities(employeeResponses)

        // Assert: Verify that the mapping is correct
        assertEquals(2, result.size)
        assertEquals(1, result[0].employeeId)
        assertEquals("John", result[0].employeeName)
        assertEquals(50000, result[0].employeeSalary)
        assertEquals(30, result[0].employeeAge)
        assertEquals(2, result[1].employeeId)
        assertEquals("Alice", result[1].employeeName)
        assertEquals(60000, result[1].employeeSalary)
        assertEquals(28, result[1].employeeAge)
    }

    @Test
    fun `mapEntitiesToDomain should correctly map EmployeeEntity list to Employee list`() {
        // Arrange: Create a list of EmployeeEntity objects
        val employeeEntities = listOf(
            EmployeeEntity(1, "John", 50000, 30),
            EmployeeEntity(2, "Alice", 60000, 28)
        )

        // Act: Call the mapping function
        val result = DataMapper.mapEntitiesToDomain(employeeEntities)

        // Assert: Verify that the mapping is correct
        assertEquals(2, result.size)
        assertEquals(1, result[0].id)
        assertEquals("John", result[0].name)
        assertEquals(50000, result[0].salary)
        assertEquals(30, result[0].age)
        assertEquals(2, result[1].id)
        assertEquals("Alice", result[1].name)
        assertEquals(60000, result[1].salary)
        assertEquals(28, result[1].age)
    }

    @Test
    fun `mapDomainToEntity should correctly map Employee object to EmployeeEntity`() {
        // Arrange: Create an Employee object
        val employee = Employee(1, "John", 50000, 30)

        // Act: Call the mapping function
        val result = DataMapper.mapDomainToEntity(employee)

        // Assert: Verify that the mapping is correct
        assertEquals(1, result.employeeId)
        assertEquals("John", result.employeeName)
        assertEquals(50000, result.employeeSalary)
        assertEquals(30, result.employeeAge)
    }
}
