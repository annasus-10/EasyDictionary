package com.example.easydictionary

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object WordsApi {
    private const val BASE_URL = "https://wordsapiv1.p.rapidapi.com/"
    private const val API_KEY = "116a20cdbfmshd64e5d5964e6b50p1a5a7ejsn7936560203c3"
    private const val API_HOST = "wordsapiv1.p.rapidapi.com"

    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        val request: Request = chain.request().newBuilder()
            .addHeader("X-RapidAPI-Key", API_KEY)
            .addHeader("X-RapidAPI-Host", API_HOST)
            .build()
        chain.proceed(request)
    }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: WordsApiService by lazy {
        retrofit.create(WordsApiService::class.java)
    }
}

interface WordsApiService {
    @GET("words/{word}")
    suspend fun getMeaning(@Path("word") word: String): retrofit2.Response<WordResult>
}
