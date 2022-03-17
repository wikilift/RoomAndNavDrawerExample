package com.wikilift.tfg.data.model.room.entity

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize



data class PetWithTreatments
    (
    @Embedded
    val petBase: PetBase,
    @Relation(
        parentColumn = "id",
        entityColumn = "petId"
    )
    val list: List<PetTreatment>
)
