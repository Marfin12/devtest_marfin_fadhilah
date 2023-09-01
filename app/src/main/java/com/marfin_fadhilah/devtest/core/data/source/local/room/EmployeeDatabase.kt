package com.marfin_fadhilah.devtest.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marfin_fadhilah.devtest.core.data.source.local.entity.EmployeeEntity


@Database(entities = [EmployeeEntity::class], version = 1, exportSchema = false)
abstract class EmployeeDatabase : RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao
}