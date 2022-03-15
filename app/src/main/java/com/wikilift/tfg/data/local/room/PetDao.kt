package com.wikilift.tfg.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.wikilift.tfg.data.model.room.entity.PetEntity

@Dao
interface PetDao {
    @Query("SELECT * FROM pet_table")
    suspend fun getAll():MutableList<PetEntity>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPet(pet: PetEntity)
    @Query("DELETE FROM pet_table WHERE id = :petId")
    suspend fun deletePet(petId:Int)
    @Query("SELECT * FROM pet_table WHERE id = :petId")
    suspend fun getPet(petId: Int):PetEntity


}