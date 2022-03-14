package com.wikilift.tfg.data.model

import java.util.*

data class PetBase(val name:String,
                   val type:String,
                   val race:String="única",
                   val sexType:Boolean,
                   val birthDate: Date,
                   var weight:Double=0.0,
                   val haveChip:Boolean=false,
                   val chipNumber:String="No tiene")

