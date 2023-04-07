package io.github.mayconcardoso.networking

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
  operator fun invoke(apiURL: String, httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(apiURL)
    .client(httpClient)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
}