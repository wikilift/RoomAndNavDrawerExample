package com.wikilift.tfg.domain.remote

import android.location.Location
import com.wikilift.tfg.data.remote.GoogleApiCall

class GetVetsImpl(private val repo:GoogleApiCall):GetVets {
    override suspend fun getVets(loc: Location,apiKey:String) =repo.makeApiCall(loc,apiKey)
}