package com.wikilift.tfg.data.model.room.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.IgnoredOnParcel
import java.io.Serializable
import java.util.*
@Entity(tableName = "pet_table")
@Parcelize
data class PetBase(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    val type: String,
    val race: String = "única",
    val sexType: Boolean,
    var imgPath: String,
    val birthDate: Date,
    var weight: Double = 0.0,
    val haveChip: Boolean = false,
    val chipNumber: String = "No tiene",


) : Parcelable{
    @IgnoredOnParcel
   @Ignore
    val listOfTreatments: List<PetTreatment> = emptyList()
}


class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}