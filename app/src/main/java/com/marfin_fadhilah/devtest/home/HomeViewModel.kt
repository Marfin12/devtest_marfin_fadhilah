package com.marfin_fadhilah.devtest.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.marfin_fadhilah.devtest.core.data.Resource
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeCallResponse
import com.marfin_fadhilah.devtest.core.domain.model.Employee
import com.marfin_fadhilah.devtest.core.domain.usecase.EmployeeUseCase


class HomeViewModel(private val employeeUseCase: EmployeeUseCase) : ViewModel() {
    private val _postResult = MutableLiveData<Resource<EmployeeCallResponse>>()
    var postResult: LiveData<Resource<EmployeeCallResponse>> = _postResult

    fun postEmployee(employee: Employee) {
        postResult = employeeUseCase.postEmployee(employee).asLiveData()
    }
}
