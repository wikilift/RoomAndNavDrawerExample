package com.wikilift.tfg.domain

import com.wikilift.tfg.data.local.datasource.PetLocalDataSourceImpl
import com.wikilift.tfg.data.model.room.entity.PetBase

class PetRepoImpl(private val repo: PetLocalDataSourceImpl) : PetRepo {
    override suspend fun getAll(): List<PetBase> = repo.getAll()
    override suspend fun update(pet: PetBase) =repo.updatePet(pet)


    override suspend fun insertPet(pet: PetBase) = repo.insertPet(pet)

    override suspend fun deletePet(pet: PetBase) = repo.deletePet(pet)
}