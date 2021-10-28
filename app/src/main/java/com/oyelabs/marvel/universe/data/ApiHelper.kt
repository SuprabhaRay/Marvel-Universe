package com.oyelabs.marvel.universe.data

import com.oyelabs.marvel.universe.tools.Utils

class ApiHelper(private val apiService: CharacterApi) {

    private val utils= Utils()
    private val apiKey= utils.getGenKeyUser()["apikey"]
    private val ts= utils.getGenKeyUser()["ts"]
    private val hash= utils.getGenKeyUser()["hash"]

    suspend fun getCharactersList(offset: Int)= apiService.getCharactersList(10, offset, ts, apiKey,
        hash)

    suspend fun getCharacterById(characterId: Int) = apiService.getCharacterById(characterId, ts,
        apiKey, hash)

    suspend fun getCharacterByName(nameStartsWith: String, offset: Int)= apiService.getCharacterByName(10,
        offset, ts, apiKey, hash, nameStartsWith)
}