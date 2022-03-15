package com.wikilift.tfg.core.extensions

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import java.io.File
import java.util.*

class Utils {
    companion object{
        @RequiresApi(Build.VERSION_CODES.N)
        fun getNamePicture():String{
            val formatter = SimpleDateFormat("yyyy_MM_dd", Locale("es", "ES"))
            val now = Date()
            return formatter.format(now).toString()

        }
        fun fileExists(url:String):Boolean= File(url).exists()

    }
}