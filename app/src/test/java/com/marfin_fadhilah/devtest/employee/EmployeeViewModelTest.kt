package com.marfin_fadhilah.devtest.employee

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marfin_fadhilah.devtest.DataDummy
import com.marfin_fadhilah.devtest.MainDispatcherRule
import com.marfin_fadhilah.devtest.core.data.EmployeeRepository
import com.marfin_fadhilah.devtest.core.data.Resource
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeCallResponse
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeListResponse
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeResponse
import com.marfin_fadhilah.devtest.core.domain.model.Employee
import com.marfin_fadhilah.devtest.core.domain.usecase.EmployeeInteraction
import com.marfin_fadhilah.devtest.core.domain.usecase.EmployeeUseCase
import com.marfin_fadhilah.devtest.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class EmployeeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var employeeRepository: EmployeeRepository

    private lateinit var employeeListViewModel: EmployeeListViewModel

    private val dummyEmployee = Employee(
        DataDummy.dummyId, DataDummy.dummyName, DataDummy.dummySalary, DataDummy.dummyAge
    )

    @Before
    fun setUp() {
        employeeListViewModel = EmployeeListViewModel(EmployeeInteraction(employeeRepository))
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when fetch employee success should return success and retrieve correct data`() = runTest {
        val expectedResponse = flowOf(
            Resource.Success(
                data = listOf(dummyEmployee, dummyEmployee),
            )
        )

        Mockito.`when`(employeeRepository.getAllEmployee()).thenReturn(expectedResponse)

        employeeListViewModel.refreshEmployee()

        val status = employeeListViewModel.employee.getOrAwaitValue()
        val data = status.data ?: listOf()

        Assert.assertTrue(status is Resource.Success)
        Assert.assertTrue(data.isNotEmpty())
        Assert.assertTrue(data[0].name == dummyEmployee.name)
    }

    @Test
    fun `when delete employee response is not error and not empty should return success`() =
        runTest {
            val expectedResponse = flowOf(
                Resource.Success(
                    data = EmployeeCallResponse(
                        "success",
                        ""
                    )
                )
            )

            Mockito.`when`(employeeRepository.deleteEmployee(dummyEmployee))
                .thenReturn(expectedResponse)

            employeeListViewModel.deleteEmployee(dummyEmployee)

            val status = employeeListViewModel.deleteResult.getOrAwaitValue()

            Assert.assertTrue(status is Resource.Success)
            Assert.assertTrue(status.data?.status == "success")
        }

    @Test
    fun `when delete employee response is error should return error`() = runTest {
        val expectedResponse = flowOf(
            Resource.Error(
                data = EmployeeCallResponse(),
                message = "some error message"
            )
        )

        Mockito.`when`(employeeRepository.deleteEmployee(dummyEmployee))
            .thenReturn(expectedResponse)

        employeeListViewModel.deleteEmployee(dummyEmployee)

        val status = employeeListViewModel.deleteResult.getOrAwaitValue()

        Assert.assertTrue(status is Resource.Error)
        Assert.assertTrue(status.data?.status == "")
    }
}