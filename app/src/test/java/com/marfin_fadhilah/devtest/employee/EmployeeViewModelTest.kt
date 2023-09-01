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

    @Mock
    private lateinit var employeeUseCase: EmployeeUseCase

    private lateinit var employeeListViewModel: EmployeeListViewModel

    val dummyEmployee = Employee(
        DataDummy.dummyId, DataDummy.dummyName, DataDummy.dummySalary, DataDummy.dummyAge
    )

    @Before
    fun setUp() {
        employeeListViewModel = EmployeeListViewModel(employeeUseCase)
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when fetch employee success`() = runTest {
        val expectedResponse = flowOf(
            Resource.Success(
                data = listOf(
                    Employee(
                        dummyEmployee.id ?: -1,
                        dummyEmployee.name,
                        dummyEmployee.salary,
                        dummyEmployee.age
                    )
                ),
            )
        )

        Mockito.`when`(employeeRepository.getAllEmployee())
            .thenReturn(expectedResponse)

        val status = employeeListViewModel.employee.getOrAwaitValue()

        Assert.assertTrue(status == expectedResponse)
        Assert.assertFalse(status.data.isNullOrEmpty())
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

            Mockito.`when`(employeeRepository.postEmployee(dummyEmployee))
                .thenReturn(expectedResponse)

            employeeListViewModel.deleteEmployee(dummyEmployee)

            val status = employeeListViewModel.deleteResult.getOrAwaitValue()

            Assert.assertTrue(status == expectedResponse)
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

        Mockito.`when`(employeeRepository.postEmployee(dummyEmployee))
            .thenReturn(expectedResponse)

        employeeListViewModel.deleteEmployee(dummyEmployee)

        val status = employeeListViewModel.deleteResult.getOrAwaitValue()

        Assert.assertTrue(status == expectedResponse)
        Assert.assertFalse(status.data?.message.isNullOrEmpty())
    }
}