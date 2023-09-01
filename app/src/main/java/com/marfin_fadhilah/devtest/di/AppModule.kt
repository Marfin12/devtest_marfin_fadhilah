package com.marfin_fadhilah.devtest.di

import com.marfin_fadhilah.devtest.core.domain.usecase.EmployeeInteraction
import com.marfin_fadhilah.devtest.core.domain.usecase.EmployeeUseCase
import com.marfin_fadhilah.devtest.employee.EmployeeListViewModel
import com.marfin_fadhilah.devtest.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    single<EmployeeUseCase> { EmployeeInteraction(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { EmployeeListViewModel(get()) }
}
