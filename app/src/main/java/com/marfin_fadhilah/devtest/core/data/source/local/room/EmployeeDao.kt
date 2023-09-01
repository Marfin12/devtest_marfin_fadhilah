package com.marfin_fadhilah.devtest.core.data.source.local.room

import androidx.room.*
import com.marfin_fadhilah.devtest.core.data.source.local.entity.EmployeeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmployeeDao {
    @Query("SELECT * FROM employee")
    fun getAllEmployee(): Flow<List<EmployeeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployee(employee: List<EmployeeEntity>)

    @Delete
    fun deleteEmployee(employee: EmployeeEntity)

    @Update
    fun updateEmployee(employee: EmployeeEntity)
}