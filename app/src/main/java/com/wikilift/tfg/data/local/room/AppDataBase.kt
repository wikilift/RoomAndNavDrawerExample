package com.wikilift.tfg.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.wikilift.tfg.data.model.room.entity.PetEntity


@Database(entities = [PetEntity::class], version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun productDao(): PetDao
    companion object{
        private var INSTANCE: AppDatabase?=null
        fun getDatabase(context: Context): AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java,"pet_table"
            ).build()
            return INSTANCE!!
        }
        fun destroyInstance(){
            INSTANCE =null}
    }
}