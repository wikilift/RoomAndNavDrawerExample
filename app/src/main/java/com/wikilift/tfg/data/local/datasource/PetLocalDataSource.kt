package com.wikilift.tfg.data.local.datasource

import com.wikilift.tfg.data.model.PetBase

interface PetLocalDataSource {
    suspend fun getAll():MutableList<PetBase>
    suspend fun getPet(petId:Int):PetBase
    suspend fun deletePet(petId: Int)
    suspend fun insertPet(pet:PetBase)
}