package com.wikilift.tfg.data.local.room

import androidx.room.*
import com.wikilift.tfg.data.model.room.entity.PetBase
import com.wikilift.tfg.data.model.room.entity.PetWithTreatments


@Dao
interface PetDao {
    @Query("SELECT * FROM pet_table")
    suspend fun getAll():List<PetBase>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPet(pet: PetBase)
    @Delete
    suspend fun deletePet(pet: PetBase)
    @Update
    suspend fun updatePet(pet: PetBase)
   @Transaction
    @Query("SELECT * FROM pet_table WHERE id = :petId")
    suspend fun getTreatmentsWithPet(petId:Int):List<PetBase>


}