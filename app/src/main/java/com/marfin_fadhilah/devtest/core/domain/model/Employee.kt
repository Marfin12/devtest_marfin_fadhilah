package com.marfin_fadhilah.devtest.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Employee(
    val id: Int? = null,
    val name: String,
    val salary: Int,
    val age: Int
): Parcelable