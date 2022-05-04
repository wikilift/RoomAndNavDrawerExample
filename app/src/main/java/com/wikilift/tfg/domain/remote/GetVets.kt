package com.wikilift.tfg.domain.remote

import android.location.Location

interface GetVets {
    suspend fun getVets(loc:Location,apiKey:String)
}