package com.wikilift.tfg.data.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

data class PetBase(
    val id:Int=0,
    val name:String,
    val type:String,
    val race:String="única",
    val sexType:Boolean,
    var imgPath:String,
    val birthDate: Date,
    var weight:Double=0.0,
    val haveChip:Boolean=false,
    val chipNumber:String="No tiene"):Serializable



