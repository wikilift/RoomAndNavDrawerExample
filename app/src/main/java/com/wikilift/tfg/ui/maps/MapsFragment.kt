package com.wikilift.tfg.ui.maps

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.wikilift.tfg.R
import com.wikilift.tfg.core.LOCATION_PERMISSION_REQ_CODE
import com.wikilift.tfg.core.TAG
import com.wikilift.tfg.core.uiutils.Result
import com.wikilift.tfg.data.remote.GoogleApiCall
import com.wikilift.tfg.databinding.FragmentMapsBinding
import com.wikilift.tfg.domain.remote.GetVets
import com.wikilift.tfg.domain.remote.GetVetsImpl
import com.wikilift.tfg.presentation.remote.GetVetsViewModel
import com.wikilift.tfg.presentation.remote.GetVetsViewModelFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await


class MapsFragment : Fragment(R.layout.fragment_maps) {
    private lateinit var binding: FragmentMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private  var longitude: Double?=null
    private  var latitude: Double?=null

    private val viewModel by viewModels<GetVetsViewModel> {
        GetVetsViewModelFactory(
            GetVetsImpl(
                GoogleApiCall()
            )
        )
    }
    private val callback = OnMapReadyCallback { googleMap ->

        //map=googleMap

        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
         googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        getCurrentLocation()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_maps, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment!!.getMapAsync { mMap ->
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

            mMap.clear() //clear old markers

            val googlePlex = CameraPosition.builder()
                .target(LatLng(37.4219999, -122.0862462))
                .zoom(10f)
                .bearing(0f)
                .tilt(45f)
                .build()

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null)

            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(37.4219999, -122.0862462))
                    .title("Spider Man")
                    .icon(bitmapDescriptorFromVector(activity, R.drawable.ic_pug_4862083))
            )

            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(37.4629101, -122.2449094))
                    .title("Iron Man")
                    .snippet("His Talent : Plenty of money")
            )

            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(37.3092293, -122.1136845))
                    .title("Captain America")
            )
        }

        return rootView
    }


    private fun bitmapDescriptorFromVector(context: Context?, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(requireContext(), vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap =
            Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMapsBinding.bind(view)




        val mapFragment = parentFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)




    }

    private fun getCurrentLocation() {
        // checking location permission

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // request permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQ_CODE
            )
            return
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                 latitude = location.latitude
                 longitude = location.longitude

               makeApiCall(location)
            }
            .addOnFailureListener {
                Toast.makeText(
                    requireContext(), "Failed on getting current location",
                    Toast.LENGTH_SHORT
                ).show()

            }

    }

    private fun makeApiCall(location: Location) {

            viewModel.getVets(location,resources.getString(R.string.GOOGLE_MAPS_KEY)).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {

                    }
                    is Result.Succes -> {
                        Log.d(TAG,"Succes")
                    }
                    is Result.Failure -> {
                        Log.d(TAG, result.exception.toString())
                    }

                }

            }
    }

    private fun openMap(latitude: Double, longitude: Double) {
        val uri = Uri.parse("geo:${latitude},${longitude}")
        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        //https://www.google.com/maps/?q=-15.623037,18.388672
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

}