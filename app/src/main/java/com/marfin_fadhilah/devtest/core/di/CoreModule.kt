package com.marfin_fadhilah.devtest.core.di

import androidx.room.Room
import com.marfin_fadhilah.devtest.BASE_URL
import com.marfin_fadhilah.devtest.DB_NAME
import com.marfin_fadhilah.devtest.core.data.EmployeeRepository
import com.marfin_fadhilah.devtest.core.data.source.local.LocalDataSource
import com.marfin_fadhilah.devtest.core.data.source.local.room.EmployeeDatabase
import com.marfin_fadhilah.devtest.core.data.source.remote.RemoteDataSource
import com.marfin_fadhilah.devtest.core.data.source.remote.network.ApiService
import com.marfin_fadhilah.devtest.core.domain.repository.IEmployeeRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val databaseModule = module {
    val factoryEncrypt = SupportFactory(SQLiteDatabase.getBytes("secret".toCharArray()))

    factory { get<EmployeeDatabase>().employeeDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            EmployeeDatabase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factoryEncrypt)
            .build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IEmployeeRepository> { EmployeeRepository(get(), get()) }
}