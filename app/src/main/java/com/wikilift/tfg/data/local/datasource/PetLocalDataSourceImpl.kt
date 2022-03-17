package com.wikilift.tfg.data.local.datasource

import com.wikilift.tfg.data.local.room.PetDao
import com.wikilift.tfg.data.model.room.entity.PetBase



class PetLocalDataSourceImpl(private val petDao: PetDao):PetLocalDataSource {
     override suspend fun getAll():List<PetBase> = petDao.getAll()
    override suspend fun updatePet(pet: PetBase) {
        petDao.updatePet(pet)
    }

    override suspend fun deletePet(pet: PetBase){
        petDao.deletePet(pet)
    }
     override suspend fun insertPet(pet: PetBase){
        petDao.insertPet(pet)
    }
}