package com.wikilift.tfg.domain

import com.wikilift.tfg.data.model.room.entity.PetBase

interface PetRepo {
    suspend fun getAll():List<PetBase>
    suspend fun update(pet: PetBase)
    suspend fun insertPet(pet: PetBase)
    suspend fun deletePet(pet: PetBase)
}