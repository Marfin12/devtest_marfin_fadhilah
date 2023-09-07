package com.marfin_fadhilah.devtest.home

import androidx.lifecycle.*
import com.marfin_fadhilah.devtest.core.data.Resource
import com.marfin_fadhilah.devtest.core.data.source.remote.response.EmployeeCallResponse
import com.marfin_fadhilah.devtest.core.domain.model.Employee
import com.marfin_fadhilah.devtest.core.domain.usecase.EmployeeUseCase
import kotlinx.coroutines.launch


class HomeViewModel(private val employeeUseCase: EmployeeUseCase) : ViewModel() {
    private val _postResult = MutableLiveData<Resource<EmployeeCallResponse>>()
    var postResult: MutableLiveData<Resource<EmployeeCallResponse>> = _postResult

    fun postEmployee(employee: Employee) {
        viewModelScope.launch {
            employeeUseCase.postEmployee(employee).collect { resource ->
                _postResult.value = resource
            }
        }
    }
}
