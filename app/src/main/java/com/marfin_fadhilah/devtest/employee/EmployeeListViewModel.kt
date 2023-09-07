package com.marfin_fadhilah.devtest.employee

import androidx.lifecycle.*
import com.marfin_fadhilah.devtest.core.data.Resource
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeCallResponse
import com.marfin_fadhilah.devtest.core.domain.model.Employee
import com.marfin_fadhilah.devtest.core.domain.usecase.EmployeeUseCase
import kotlinx.coroutines.launch


class EmployeeListViewModel(private val employeeUseCase: EmployeeUseCase) : ViewModel() {
    private val _editResult = MutableLiveData<Resource<EmployeeCallResponse>>()
    var editResult: MutableLiveData<Resource<EmployeeCallResponse>> = _editResult

    private val _deleteResult = MutableLiveData<Resource<EmployeeCallResponse>>()
    var deleteResult: MutableLiveData<Resource<EmployeeCallResponse>> = _deleteResult

    private val _employee = MutableLiveData<Resource<List<Employee>>>()
    var employee = _employee

    fun refreshEmployee() {
        viewModelScope.launch {
            employeeUseCase.getAllEmployee().collect { resource ->
                _employee.value = resource
            }
        }
    }

    fun deleteEmployee(employee: Employee) {
        viewModelScope.launch {
            employeeUseCase.deleteEmployee(employee).collect { resource ->
                _deleteResult.value = resource
            }
        }
    }

    fun updateEmployee(employee: Employee) {
        viewModelScope.launch {
            employeeUseCase.updateEmployee(employee).collect { resource ->
                _editResult.value = resource
            }
        }
    }
}