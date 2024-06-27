package com.example.quotegenerator

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitInstance {
    //base url for the API
    private const val BASE_URL = "https://zenquotes.io/api/"

    //function to create and configure a retrofit instance 
    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) //handles JSON deserialization
            .build()

    }
    //Quote API instance... The QuoteAPI interface will be implemented by Retrofit, providing the API's methods
    val quoteApi : QuoteAPI = getInstance().create(QuoteAPI::class.java)
}
