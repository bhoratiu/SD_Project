package com.mtd.electrica.common.baseModelPackage.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.mtd.electrica.common.roomDb.AppDatabase
import com.mtd.electrica.utils.RetrofitConstants.BASE_URL
import com.mtd.electrica.utils.RoomConstants.ELECTRICA_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class CommonModule {

    @Provides
    fun provideHttpLoggingInterceptor(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)

        return httpClient.build()
    }

    @Provides
    @Named("Service8085")
    fun provideRetrofitService8085(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl("http://10.132.66.30:55008/")
       // .baseUrl("http://10.0.2.2:8085/") for localhostz
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Named("Service8086")
    fun provideRetrofitService8086(
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit
        .Builder()
        .baseUrl("http://10.132.66.30:55010/")
        //        .baseUrl("http://10.0.2.2:8086/") for localhost
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room
            .databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                ELECTRICA_DATABASE_NAME

            )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
    }
}