package com.marfin_fadhilah.devtest

import android.app.Application
import com.marfin_fadhilah.devtest.core.di.databaseModule
import com.marfin_fadhilah.devtest.core.di.networkModule
import com.marfin_fadhilah.devtest.core.di.repositoryModule
import com.marfin_fadhilah.devtest.di.useCaseModule
import com.marfin_fadhilah.devtest.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

open class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}