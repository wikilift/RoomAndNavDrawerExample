package com.wikilift.tfg.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.wikilift.tfg.core.uiutils.Result
import com.wikilift.tfg.data.model.PetBase
import com.wikilift.tfg.domain.PetRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class PetViewModel(private val repo: PetRepo) : ViewModel() {

    fun getAll() = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try{
            emit(Result.Succes(repo.getAll()))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }

    fun getPet(petId:Int) = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try{
            emit(Result.Succes(repo.getPet(petId = petId)))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }
    fun insertPet(pet:PetBase) = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try{
            emit(Result.Succes(repo.insertPet(pet)))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }
    fun deletePet(petId:Int) = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try{
            emit(Result.Succes(repo.deletePet(petId)))
        }catch (e:Exception){
            emit(Result.Failure(e))
        }
    }
}


class PetViewModelFactory(private val repo: PetRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PetViewModel(repo) as T
    }
}