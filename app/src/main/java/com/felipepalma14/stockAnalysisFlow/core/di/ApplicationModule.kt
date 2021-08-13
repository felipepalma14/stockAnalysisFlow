package com.felipepalma14.stockAnalysisFlow.core.di

import com.felipepalma14.stockAnalysisFlow.BuildConfig
import com.felipepalma14.stockAnalysisFlow.features.data.remote.api.MonteBravoApi
import com.felipepalma14.stockAnalysisFlow.features.data.repository.StockRepositoryImp
import com.felipepalma14.stockAnalysisFlow.features.domain.repository.IStockRepository
import com.felipepalma14.stockAnalysisFlow.features.domain.usecase.GetStockUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(createClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        //if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        //}
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): MonteBravoApi {
        return retrofit.create(MonteBravoApi::class.java)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class BindsModule {
    @Binds
    abstract fun bindStockRepository(repository: StockRepositoryImp): IStockRepository
}
