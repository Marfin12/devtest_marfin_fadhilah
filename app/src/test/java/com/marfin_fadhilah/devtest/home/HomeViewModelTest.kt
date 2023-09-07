package com.marfin_fadhilah.devtest.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.marfin_fadhilah.devtest.DataDummy
import com.marfin_fadhilah.devtest.MainDispatcherRule
import com.marfin_fadhilah.devtest.core.data.EmployeeRepository
import com.marfin_fadhilah.devtest.core.data.Resource
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeCallResponse
import com.marfin_fadhilah.devtest.core.domain.model.Employee
import com.marfin_fadhilah.devtest.core.domain.usecase.EmployeeInteraction
import com.marfin_fadhilah.devtest.core.domain.usecase.EmployeeUseCase
import com.marfin_fadhilah.devtest.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var employeeRepository: EmployeeRepository

    private lateinit var homeViewModel: HomeViewModel

    private val dummyEmployee = Employee(
        DataDummy.dummyId, DataDummy.dummyName, DataDummy.dummySalary, DataDummy.dummyAge
    )

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(EmployeeInteraction(employeeRepository))
    }

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when employee response is not error and not empty should return success`() = runTest {
        val expectedResponse = flowOf(Resource.Success(data = EmployeeCallResponse(
            "success",
            ""
        )))

        `when`(employeeRepository.postEmployee(dummyEmployee))
            .thenReturn(expectedResponse)

        homeViewModel.postEmployee(dummyEmployee)

        val status = homeViewModel.postResult.getOrAwaitValue()

        Assert.assertTrue(status is Resource.Success)
        Assert.assertTrue(status.data?.status == "success")
    }

    @Test
    fun `when employee response is error should return error`() = runTest {
        val expectedResponse = flowOf(Resource.Error(data = EmployeeCallResponse(),
            message = "some error message"
        ))

        `when`(employeeRepository.postEmployee(dummyEmployee))
            .thenReturn(expectedResponse)

        homeViewModel.postEmployee(dummyEmployee)

        val status = homeViewModel.postResult.getOrAwaitValue()

        Assert.assertTrue(status is Resource.Error)
        Assert.assertTrue(status.data?.status == "")
    }
}
