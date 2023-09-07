package com.marfin_fadhilah.devtest.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.marfin_fadhilah.devtest.DataDummy
import com.marfin_fadhilah.devtest.MainDispatcherRule
import com.marfin_fadhilah.devtest.core.data.EmployeeRepository
import com.marfin_fadhilah.devtest.core.data.source.local.LocalDataSource
import com.marfin_fadhilah.devtest.core.data.source.local.entity.EmployeeEntity
import com.marfin_fadhilah.devtest.core.data.source.remote.RemoteDataSource
import com.marfin_fadhilah.devtest.core.data.source.remote.network.ApiResponse
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeCallResponse
import com.marfin_fadhilah.devtest.data.fake.FakeEmployeeDao
import com.marfin_fadhilah.devtest.utils.observeForTesting
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
class EmployeeRepositoryTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var localDataSource: LocalDataSource

    private lateinit var employeeDao: FakeEmployeeDao
    private lateinit var employeeRepository: EmployeeRepository

    private val dummyEmployeeEntity = EmployeeEntity(
        55,
        DataDummy.dummyName,
        DataDummy.dummySalary,
        DataDummy.dummyAge
    )

    @Before
    fun setUp() {
        employeeDao = FakeEmployeeDao()
        employeeRepository = EmployeeRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `when get employees should still get employeeResult`() =
        runTest {
            Mockito.`when`(localDataSource.getAllEmployee()).thenReturn(
                flowOf(listOf(dummyEmployeeEntity))
            )

            val employeeResponse = employeeRepository.getAllEmployee().asLiveData()

            employeeResponse.observeForTesting {
                Assert.assertFalse(employeeResponse.value?.data.isNullOrEmpty())
            }
        }

    @Test
    fun `when get employees should retrieve error`() =
        runTest {
            Mockito.`when`(remoteDataSource.getAllEmployee())
                .thenReturn(flowOf(ApiResponse.Error("some error")))
            Mockito.`when`(localDataSource.getAllEmployee()).thenReturn(
                flowOf(listOf())
            )

            val employeeResponse = employeeRepository.getAllEmployee().asLiveData()

            employeeResponse.observeForTesting {
                Assert.assertTrue(employeeResponse.value?.data.isNullOrEmpty())
            }
        }

    @Test
    fun `when delete employees should success`() =
        runTest {
            Mockito.`when`(remoteDataSource.deleteEmployee(DataDummy.dummyEmployee.id.toString()))
                .thenReturn(
                    flowOf(
                        ApiResponse.Success(
                            EmployeeCallResponse(
                                "success",
                                "some message"
                            )
                        )
                    )
                )
            val deleteResponse =
                employeeRepository.deleteEmployee(DataDummy.dummyEmployee).asLiveData()

            deleteResponse.observeForTesting {
                Assert.assertNotNull(deleteResponse.value?.data)
                Assert.assertTrue(deleteResponse.value?.data?.status == "success")
            }
        }

    @Test
    fun `when delete employees error`() =
        runTest {
            Mockito.`when`(remoteDataSource.deleteEmployee(DataDummy.dummyEmployee.id.toString()))
                .thenReturn(flowOf(ApiResponse.Error("some error")))

            val deleteResponse =
                employeeRepository.deleteEmployee(DataDummy.dummyEmployee).asLiveData()

            deleteResponse.observeForTesting {
                Assert.assertNull(deleteResponse.value?.data)
                Assert.assertTrue(deleteResponse.value?.message == "some error")
            }
        }

    @Test
    fun `when update employees should success`() =
        runTest {
            Mockito.`when`(remoteDataSource.updateEmployee(DataDummy.dummyEmployee))
                .thenReturn(
                    flowOf(
                        ApiResponse.Success(
                            EmployeeCallResponse(
                                "success",
                                "some message"
                            )
                        )
                    )
                )
            val uploadResponse =
                employeeRepository.updateEmployee(DataDummy.dummyEmployee).asLiveData()

            uploadResponse.observeForTesting {
                Assert.assertNotNull(uploadResponse)
                Assert.assertTrue(uploadResponse.value?.data?.status == "success")
            }
        }

    @Test
    fun `when update employees error`() =
        runTest {
            Mockito.`when`(remoteDataSource.updateEmployee(DataDummy.dummyEmployee))
                .thenReturn(flowOf(ApiResponse.Error("some error")))

            val uploadResponse =
                employeeRepository.updateEmployee(DataDummy.dummyEmployee).asLiveData()

            uploadResponse.observeForTesting {
                Assert.assertNull(uploadResponse.value?.data)
                Assert.assertTrue(uploadResponse.value?.message == "some error")
            }
        }
}