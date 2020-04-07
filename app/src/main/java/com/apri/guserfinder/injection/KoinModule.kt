package com.apri.guserfinder.injection

import com.apri.guserfinder.datasource.GithubAPI
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//
//  Project: GUserFinder
//  Author: dwiaprianto (dwiaprianto22@gmail.com)
//  Date: 07/04/20
//
//  Copyright © 2019 dwiaprianto. All rights reserved.
//

val viewModelModule = module {
}


const val BASE_URL = "https://api.github.com//"
val apiModule = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }
    single { get<Retrofit>().create(GithubAPI::class.java) }
}