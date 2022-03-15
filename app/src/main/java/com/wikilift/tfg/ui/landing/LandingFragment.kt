package com.wikilift.tfg.ui.landing

import android.app.Activity
import android.content.ContentValues

import android.graphics.Bitmap
import android.graphics.ImageDecoder

import android.net.Uri
import android.os.Build
import android.os.Bundle

import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.github.drjacky.imagepicker.ImagePicker
import com.wikilift.tfg.R
import com.wikilift.tfg.core.extensions.Utils
import com.wikilift.tfg.core.extensions.makeSnack
import com.wikilift.tfg.data.local.datasource.PetLocalDataSourceImpl
import com.wikilift.tfg.data.local.room.AppDatabase
import com.wikilift.tfg.databinding.FragmentLandingBinding
import com.wikilift.tfg.domain.PetRepoImpl
import com.wikilift.tfg.presentation.PetViewModel
import com.wikilift.tfg.presentation.PetViewModelFactory


class LandingFragment : Fragment(R.layout.fragment_landing) {

    private lateinit var binding: FragmentLandingBinding

    private val viewModel by viewModels<PetViewModel> {
        PetViewModelFactory(
            PetRepoImpl(
                PetLocalDataSourceImpl(AppDatabase.getDatabase(requireContext()).productDao())
            )
        )
    }


    private var bitmap: Bitmap? = null


    @RequiresApi(Build.VERSION_CODES.P)
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(requireActivity().contentResolver, uri)
                bitmap = ImageDecoder.decodeBitmap(source)
                binding.imgCircle.setImageURI(uri)


                val path = MediaStore.Images.Media.insertImage(
                    context?.contentResolver,
                    bitmap,
                    Utils.getNamePicture(),
                    null
                )

                binding.imgCircle.setImageURI(Uri.parse(path))
                makeSnack(requireView(), "Imagen guardada en la galería", "top")
                // saveOnDB!


            }
        }


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLandingBinding.bind(view)
        binding.btnLanding.setOnClickListener {
            try {
                ImagePicker.with(requireActivity()).createIntentFromDialog { launcher.launch(it) }

                //findNavController().navigate(R.id.action_landingFragment_to_petDetailFragment)

            } catch (e: Exception) {
                Log.d(ContentValues.TAG, e.toString())
            }
        }


    }


}