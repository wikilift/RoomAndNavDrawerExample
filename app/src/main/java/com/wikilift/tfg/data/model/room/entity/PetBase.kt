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
    var type: String="",
    var race: String = "única",
    var sexType: String="",
    var imgPath: String="",
    var birthDate: String="",
    var weight: String = "0",
    var haveChip: String="No",
    var chipNumber: String = "No tiene",


) : Parcelable

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