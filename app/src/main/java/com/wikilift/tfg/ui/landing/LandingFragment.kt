package com.wikilift.tfg.ui.landing

import android.app.Activity
import android.content.ContentValues.TAG

import android.graphics.Bitmap
import android.graphics.ImageDecoder

import android.os.Build
import android.os.Bundle

import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.wikilift.tfg.MainActivity
import com.wikilift.tfg.R
import com.wikilift.tfg.core.extensions.*
import com.wikilift.tfg.core.uiutils.Result
import com.wikilift.tfg.data.local.datasource.PetLocalDataSourceImpl
import com.wikilift.tfg.data.local.room.AppDatabase
import com.wikilift.tfg.databinding.FragmentLandingBinding

import com.wikilift.tfg.domain.PetRepoImpl
import com.wikilift.tfg.presentation.PetViewModel
import com.wikilift.tfg.presentation.PetViewModelFactory
import java.lang.Exception


class LandingFragment : Fragment(R.layout.fragment_landing), IOnBackPressed {

    private lateinit var binding: FragmentLandingBinding

    //counter back pressed
    private var counter = 1

    //dependency injection
    private val viewModel by viewModels<PetViewModel> {
        PetViewModelFactory(
            PetRepoImpl(
                PetLocalDataSourceImpl(AppDatabase.getDatabase(requireContext()).productDao())
            )
        )
    }


    private var bitmap: Bitmap? = null

    //register activity to camera launch
    @RequiresApi(Build.VERSION_CODES.P)
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(requireActivity().contentResolver, uri)
                bitmap = ImageDecoder.decodeBitmap(source)
                //binding.imgCircle.setImageURI(uri)


                val path = MediaStore.Images.Media.insertImage(
                    context?.contentResolver,
                    bitmap,
                    Utils.getNamePicture(),
                    null
                )

                //binding.imgCircle.setImageURI(Uri.parse(path))
                makeSnack(requireView(), "Imagen guardada en la galería", "top")
                // saveOnDB!


            }
        }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(firstInit){
            findNavController().navigate(R.id.action_landingFragment_to_splashFragment3)
            firstInit=false
        }
        binding = FragmentLandingBinding.bind(view)


        buttonAddRotate()
        downloadPets()



        /*else {
           val k = activity?.findViewById<View>(R.id.toolbar)
           k?.show()

       }*/

        /*try {
            ImagePicker.with(requireActivity()).createIntentFromDialog { launcher.launch(it) }

            //findNavController().navigate(R.id.action_landingFragment_to_petDetailFragment)

        } catch (e: Exception) {
            Log.d(ContentValues.TAG, e.toString())
        }*/


    }

    private fun buttonAddRotate() {
        val rotation= AnimationUtils.loadAnimation(requireContext(),R.anim.infinite_rotation)
        rotation.fillAfter=true
        binding.addButton.startAnimation(rotation)
    }

    private fun downloadPets() {
        viewModel.getAll().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {

                    binding.progressbar.show()


                }
                is Result.Succes -> {
                    binding.progressbar.hide()

                    binding.landingView.show()
                    Log.d(XX, "He acabado ${result.data.size}")
                }
                is Result.Failure -> {
                    Log.d(XX, result.exception.toString())
                }
            }
        }
    }




    override fun onBackPressed(): Boolean {
        if (counter > 0) {
            makeToast(requireContext(), "Presiona atrás otra vez para salir")
            counter--
            return false
        }
        return true

    }

    override fun onResume() {
        counter = 1
        super.onResume()
    }


}