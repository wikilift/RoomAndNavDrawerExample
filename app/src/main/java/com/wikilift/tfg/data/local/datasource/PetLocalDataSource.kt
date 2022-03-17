package com.wikilift.tfg.data.local.datasource

import com.wikilift.tfg.data.model.room.entity.PetBase

interface PetLocalDataSource {
    suspend fun getAll():List<PetBase>
    suspend fun updatePet(pet: PetBase)
    suspend fun deletePet(pet: PetBase)
    suspend fun insertPet(pet: PetBase)
}