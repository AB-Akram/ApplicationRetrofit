package com.codabee.applicationretrofit.core

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    /*
    * a companion object is a special object which is bound to (i.e. is a part of) a class definition
    * A companion object and its class can access each other's private members. A companion object's apply method lets you create new instances of a class without using the new keyword.
    */
    companion object { /* equivalent of static members in Java*/
        val mainURL = "https://jsonplaceholder.typicode.com/"

        fun getRetrofitInstance(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(mainURL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())) //Convert json to data
                .build()
        }
    }
}
