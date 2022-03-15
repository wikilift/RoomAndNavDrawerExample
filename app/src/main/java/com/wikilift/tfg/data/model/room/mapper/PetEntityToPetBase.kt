package com.wikilift.tfg.data.model.room.mapper

import com.wikilift.tfg.data.model.PetBase
import com.wikilift.tfg.data.model.room.entity.PetEntity

fun PetEntity.toPetBase():PetBase = PetBase(id,name, type, race, sexType, imgPath, birthDate, weight, haveChip, chipNumber)

fun MutableList<PetEntity>.toPetBaseList():MutableList<PetBase>{
    val resultList= mutableListOf<PetBase>()
    this.forEach{ resultList.add(it.toPetBase())}
    return resultList
}