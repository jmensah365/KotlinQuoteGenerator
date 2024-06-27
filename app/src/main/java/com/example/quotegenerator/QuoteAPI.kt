package com.example.quotegenerator
import retrofit2.Response
import retrofit2.http.GET

//creating getter api
interface QuoteAPI {
    //pass in the path of the api
    @GET("random")
    //creating an async function to get response from API
    suspend fun getRandomQuote() : Response<List<QuoteModel>>

}
