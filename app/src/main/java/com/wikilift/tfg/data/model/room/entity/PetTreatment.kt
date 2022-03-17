package com.wikilift.tfg.data.model.room.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*



data class PetTreatment(

    val treatmentId: Int = 0,
    var startDate: Date,
    val endDate: Date,
    var quantity: Int = 0,
    @PrimaryKey(autoGenerate = false)
    var nameOfPill: String = "medicamento"

)


