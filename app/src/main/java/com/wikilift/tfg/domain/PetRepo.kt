package com.wikilift.tfg.domain

import com.wikilift.tfg.data.model.PetBase

interface PetRepo {
    suspend fun getAll():MutableList<PetBase>
    suspend fun getPet(petId:Int):PetBase
    suspend fun insertPet(pet:PetBase)
    suspend fun deletePet(petId: Int)
}