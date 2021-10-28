package com.oyelabs.marvel.universe.data

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getCharacterList(offset: Int)= apiHelper.getCharactersList(offset)
    suspend fun getCharacterById(characterId: Int) = apiHelper.getCharacterById(characterId)
    suspend fun getCharacterByName(nameStartsWith: String, offset: Int)=
        apiHelper.getCharacterByName(nameStartsWith, offset)
}