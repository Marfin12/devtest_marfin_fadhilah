package com.marfin_fadhilah.devtest.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee")
data class EmployeeEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    var employeeId: Int,

    @ColumnInfo(name = "name")
    var employeeName: String,

    @ColumnInfo(name = "salary")
    var employeeSalary: Int,

    @ColumnInfo(name = "age")
    var employeeAge: Int
)