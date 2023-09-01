package com.marfin_fadhilah.devtest.employee

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.marfin_fadhilah.devtest.core.data.Resource
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeCallResponse
import com.marfin_fadhilah.devtest.core.domain.model.Employee
import com.marfin_fadhilah.devtest.core.domain.usecase.EmployeeUseCase


class EmployeeListViewModel(private val employeeUseCase: EmployeeUseCase) : ViewModel() {
    private val _editResult = MutableLiveData<Resource<EmployeeCallResponse>>()
    var editResult: LiveData<Resource<EmployeeCallResponse>> = _editResult

    private val _deleteResult = MutableLiveData<Resource<EmployeeCallResponse>>()
    var deleteResult: LiveData<Resource<EmployeeCallResponse>> = _deleteResult

    var employee = employeeUseCase.getAllEmployee().asLiveData()

    fun refreshEmployee() {
        employee = employeeUseCase.getAllEmployee().asLiveData()
    }

    fun deleteEmployee(employee: Employee) {
        deleteResult = employeeUseCase.deleteEmployee(employee).asLiveData()
    }

    fun updateEmployee(employee: Employee) {
        editResult = employeeUseCase.updateEmployee(employee).asLiveData()
    }
}