package com.oyelabs.marvel.universe.viewModel
import androidx.lifecycle.ViewModel
import com.oyelabs.marvel.universe.data.MainRepository
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.liveData
import com.oyelabs.marvel.universe.tools.Resource
import java.lang.Exception
class CharacterListViewModel(private val mainRepository: MainRepository): ViewModel() {
    fun getCharacterList(offset: Int)= liveData(Dispatchers.IO){
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getCharacterList(offset)))
        } catch (e: Exception){
            emit(Resource.error(data = null, message = e.message?: "Error occurred!"))
        }
    }
    fun getCharacterById(characterId: Int) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            emit(Resource.success(mainRepository.getCharacterById(characterId)))
        } catch (e: Exception){
            e.printStackTrace()
            emit(Resource.error(null, e.message?: "Error occurred!"))
        }
    }
    fun getCharacterByName(nameStartsWith: String, offset: Int)= liveData(Dispatchers.IO) { emit(Resource.loading(null))
    try {
        emit(Resource.success(mainRepository.getCharacterByName(nameStartsWith, offset)))
    } catch (exception: Exception){
        emit(Resource.error(null, exception.message?: "Error occurred!"))
    }}
}