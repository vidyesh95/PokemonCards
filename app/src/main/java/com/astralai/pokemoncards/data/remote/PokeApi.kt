package com.astralai.pokemoncards.data.remote

import com.astralai.pokemoncards.data.remote.responses.Pokemon
import com.astralai.pokemoncards.data.remote.responses.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApi {
    @GET(value = "pokemon")
    suspend fun getPokemonList(
        @Query(value = "limit") limit: Int,
        @Query(value = "offset") offset: Int
    ): PokemonList

    @GET(value = "pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path(value = "name") name: String
    ): Pokemon
}