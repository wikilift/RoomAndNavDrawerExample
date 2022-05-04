package com.wikilift.tfg.data.remote

import android.content.res.Resources
import android.location.Location
import android.util.Log
import com.wikilift.tfg.R
import com.wikilift.tfg.core.TAG
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await

class GoogleApiCall {
    suspend fun makeApiCall(location: Location,apiKey:String){
        val client = OkHttpClient.Builder().build()
        Log.d(TAG,"Hi, I am a big dick")
        val request = Request
            .Builder()
            .url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${location.latitude}" +
                    ",${location.longitude}&radius=1500&type=restaurant&key=$apiKey")
            .build()
      val response = client.newCall(request).await()


        Log.d(TAG,response.toString())


    }
}