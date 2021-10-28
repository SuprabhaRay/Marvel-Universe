package com.oyelabs.marvel.universe.data
import com.oyelabs.marvel.universe.model.CharacterDataWrapper
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
interface CharacterApi {
    @GET("/v1/public/characters")
    suspend fun getCharactersList(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 100,
        @Query("ts") ts: String?,
        @Query("apikey") apikey: String?,
        @Query("hash") hash: String?
    ): CharacterDataWrapper
    @GET("/v1/public/characters/{characterId}")
    suspend fun getCharacterById(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: String?,
        @Query("apikey") apikey: String?,
        @Query("hash") hash: String?
    ): CharacterDataWrapper
    @GET("/v1/public/characters")
    suspend fun getCharacterByName(
        @Query("limit") limit: Int = 100,
        @Query("offset") offset: Int = 100,
        @Query("ts") ts: String?,
        @Query("apikey") apikey: String?,
        @Query("hash") hash: String?,
        @Query("nameStartsWith") nameStartsWith: String?
    ): CharacterDataWrapper
}