package com.wikilift.tfg.data.local.datasource

import com.wikilift.tfg.data.local.room.PetDao
import com.wikilift.tfg.data.model.PetBase

import com.wikilift.tfg.data.model.room.mapper.toPetBase
import com.wikilift.tfg.data.model.room.mapper.toPetBaseList
import com.wikilift.tfg.data.model.room.mapper.toPetEntity

class PetLocalDataSourceImpl(private val petDao: PetDao):PetLocalDataSource {
     override suspend fun getAll():MutableList<PetBase> = petDao.getAll().toPetBaseList()
     override suspend fun getPet(petId:Int):PetBase = petDao.getPet(petId).toPetBase()
     override suspend fun deletePet(petId: Int){
        petDao.deletePet(petId)
    }
     override suspend fun insertPet(pet:PetBase){
        petDao.insertPet(pet.toPetEntity())
    }
}