package com.wikilift.tfg.ui.landing

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.wikilift.tfg.R
import com.wikilift.tfg.core.extensions.*
import com.wikilift.tfg.core.uiutils.Result
import com.wikilift.tfg.core.uiutils.showDialog
import com.wikilift.tfg.data.local.datasource.PetLocalDataSourceImpl
import com.wikilift.tfg.data.local.room.AppDatabase
import com.wikilift.tfg.data.model.room.entity.PetBase
import com.wikilift.tfg.databinding.FragmentLandingBinding
import com.wikilift.tfg.domain.PetRepoImpl
import com.wikilift.tfg.presentation.PetViewModel
import com.wikilift.tfg.presentation.PetViewModelFactory
import com.wikilift.tfg.ui.landing.adapter.PetAdapter


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



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        val rotation = AnimationUtils.loadAnimation(requireContext(), R.anim.infinite_rotation)
        rotation.fillAfter = true
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
                    if (result.data.isEmpty()) binding.noPetsRegistered.show()
                    binding.landingView.show()
                    listenerNewPet()
                    val list=result.data.toMutableList()
                    val listAdapter = PetAdapter(
                        list,
                        onClickListener = { view, errorCode, i ->
                            adapterMethod(
                                view,
                                errorCode,
                                i
                            )
                        })
                    binding.rvPets.adapter=listAdapter
                    Log.d(XX, "He acabado ${result.data.size}")
                }
                is Result.Failure -> {
                    Log.d(XX, result.exception.toString())
                }
            }
        }
    }

    private fun adapterMethod(view: View, errorCode: PetBase, i: Int) {

    }

    private fun listenerNewPet() {
        binding.addButton.setOnClickListener {
            showDialog(requireContext(),layoutInflater,findNavController())
        }
    }



    override fun onBackPressed(): Boolean {
        if (counter > 0) {
            makeToast(requireContext(), "Presiona atrás otra vez para salir")
            counter--
            return false
        }
        activity?.finish()
        return true

    }

    override fun onResume() {
        counter = 1
        super.onResume()
    }


}