package com.wikilift.tfg.data.model.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "pet_table")
data class PetEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    @ColumnInfo(name = "name")
    var name:String,
    @ColumnInfo(name = "type")
    val type:String,
    @ColumnInfo(name = "race")
    val race:String="única",
    @ColumnInfo(name = "sexType")
    val sexType:Boolean,
    @ColumnInfo(name = "imgPath")
    var imgPath:String,
    @ColumnInfo(name = "birthDate")
    val birthDate: Date,
    @ColumnInfo(name = "weight")
    var weight:Double=0.0,
    @ColumnInfo(name = "haveChip")
    val haveChip:Boolean=false,
    @ColumnInfo(name = "chipNumber")
    val chipNumber:String="No tiene"
): Serializable
