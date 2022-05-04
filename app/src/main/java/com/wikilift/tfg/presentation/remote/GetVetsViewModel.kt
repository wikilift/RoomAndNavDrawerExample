package com.wikilift.tfg.presentation.remote

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.wikilift.tfg.core.uiutils.Result
import com.wikilift.tfg.domain.PetRepo
import com.wikilift.tfg.domain.remote.GetVets
import com.wikilift.tfg.presentation.PetViewModel
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class GetVetsViewModel (private val repo: GetVets) : ViewModel(){

    fun getVets(loc:Location,apiKey:String) = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try{
            emit(Result.Succes(repo.getVets(loc,apiKey)))
        }catch (e: Exception){
            emit(Result.Failure(e))
        }
    }

}



class GetVetsViewModelFactory(private val repo:GetVets): ViewModelProvider.Factory{
    override fun<T:ViewModel> create (modelClass:Class<T>):T{
        return GetVetsViewModel(repo) as T
    }
}