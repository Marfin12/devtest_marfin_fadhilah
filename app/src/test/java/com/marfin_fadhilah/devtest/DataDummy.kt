package com.marfin_fadhilah.devtest

import com.marfin_fadhilah.devtest.core.domain.model.Employee

object DataDummy {
    val dummyId = 1
    val dummyName = "test@gmail.com"
    val dummySalary = 1
    val dummyAge = 3
    val dummyEmployee = Employee(
        dummyId, dummyName, dummySalary, dummyAge
    )
}