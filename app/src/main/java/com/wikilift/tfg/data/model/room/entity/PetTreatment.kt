package com.wikilift.tfg.data.model.room.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable
import java.util.*

@Parcelize
@Entity(tableName = "treatment_table")
data class PetTreatment(
    @PrimaryKey(autoGenerate = true)
    val treatmentId:Int,
    val petId: Int,
    var startDate: Date,
    val endDate: Date,
    var quantity: Int = 0,
    var nameOfPill: String = "medicamento"


):Parcelable
