package com.wikilift.tfg.domain

import com.wikilift.tfg.data.local.datasource.PetLocalDataSourceImpl
import com.wikilift.tfg.data.model.PetBase

class PetRepoImpl(private val repo: PetLocalDataSourceImpl) : PetRepo {
    override suspend fun getAll(): MutableList<PetBase> = repo.getAll()


    override suspend fun getPet(petId: Int): PetBase = repo.getPet(petId)

    override suspend fun insertPet(pet: PetBase) = repo.insertPet(pet)

    override suspend fun deletePet(petId: Int) = repo.deletePet(petId)
}