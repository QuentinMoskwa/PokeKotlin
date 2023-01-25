package com.example.pokekotlin

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokeApiClient {
    private val pokeApiService: PokeApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        pokeApiService = retrofit.create(PokeApiService::class.java)
    }

    fun getPokemon(id: Int, callback: (Pokemon?) -> Unit) {
        val call = pokeApiService.getPokemon(id)
        call.enqueue(object : Callback<Pokemon> {
            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
                callback(response.body())
            }

            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
                callback(null)
            }
        })
    }
}