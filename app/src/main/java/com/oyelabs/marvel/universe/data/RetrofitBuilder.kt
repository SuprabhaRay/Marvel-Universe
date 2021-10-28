package com.oyelabs.marvel.universe.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .build()

     val retrofitApi: CharacterApi = Retrofit.Builder()
         .client(okHttpClient)
         .baseUrl("http://gateway.marvel.com")
         .addConverterFactory(GsonConverterFactory.create())
         .build()
         .create(CharacterApi::class.java)


}