package com.gads2020.practice.network

import android.util.Log
import com.gads2020.practice.utils.Utils
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    companion object {
        private var INSTANCE: ServiceApi? = null
        private var formInstance: ServiceApi? = null
        private const val BASE_URL = "https://gadsapi.herokuapp.com/api/"
        private val gson = GsonBuilder()
            .setLenient()
            .serializeNulls()
            //.excludeFieldsWithoutExposeAnnotation()
            .create()


        private fun createClient(): OkHttpClient {
            val logging = HttpLoggingInterceptor().also {
                it.level = HttpLoggingInterceptor.Level.BODY
            }
            return OkHttpClient.Builder()
                 .addInterceptor(logging)
                 //.addInterceptor(StethoInterceptor())
                 .connectTimeout(60, TimeUnit.SECONDS)
                 .readTimeout(60, TimeUnit.SECONDS)
                 .writeTimeout(60, TimeUnit.SECONDS)
                 .build()
        }

        fun getInstance(): ServiceApi {
            Utils.showLog(" ServiceApi called")

            if (INSTANCE == null) {

                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(createClient())
                    .build()
                INSTANCE = retrofit.create(ServiceApi::class.java)
            }
            return INSTANCE!!
        }

        fun getFormInstance(): ServiceApi {

            if (formInstance == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://docs.google.com/forms/d/e/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(createClient())
                    .build()
                formInstance = retrofit.create(
                    ServiceApi::class.java
                )
            }
            return formInstance!!
        }
    }
}