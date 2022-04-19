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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.wikilift.tfg.R
import com.wikilift.tfg.core.extensions.*
import com.wikilift.tfg.core.uiutils.Result
import com.wikilift.tfg.core.uiutils.SwipeToDeleteCallbackAdapter
import com.wikilift.tfg.core.uiutils.showDialog
import com.wikilift.tfg.data.local.datasource.PetLocalDataSourceImpl
import com.wikilift.tfg.data.local.room.AppDatabase
import com.wikilift.tfg.data.model.room.entity.PetBase
import com.wikilift.tfg.databinding.FragmentLandingBinding
import com.wikilift.tfg.domain.PetRepoImpl
import com.wikilift.tfg.presentation.PetViewModel
import com.wikilift.tfg.presentation.PetViewModelFactory
import com.wikilift.tfg.ui.landing.adapter.PetAdapter
import com.wikilift.tfg.ui.petDetail.PetDetailFragmentDirections


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

    private lateinit var listAdapter: PetAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLandingBinding.bind(view)


        buttonAddRotate()
        downloadPets()


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
                    val list = result.data.toMutableList()
                    listAdapter = PetAdapter(
                        list,
                        onClickListener = { view, errorCode, i ->
                            adapterMethod(
                                view,
                                errorCode,
                                i
                            )
                        })
                    binding.rvPets.adapter = listAdapter


                    val swipeToDeleteCallbackAdapter =
                        object : SwipeToDeleteCallbackAdapter(requireContext()) {
                            override fun onSwiped(
                                viewHolder: RecyclerView.ViewHolder,
                                direction: Int
                            ) {
                                val temporal =
                                    listAdapter.returnOrder(viewHolder.absoluteAdapterPosition)
                                val pos = viewHolder.absoluteAdapterPosition


                                listAdapter.removeAt(viewHolder.absoluteAdapterPosition)
                                snackDismiss(temporal, pos)


                            }
                        }
                    val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallbackAdapter)
                    itemTouchHelper.attachToRecyclerView(binding.rvPets)

                }
                is Result.Failure -> {
                    Log.d(XX, result.exception.toString())
                }
            }
        }
    }


    private fun snackDismiss(temporal: PetBase, pos: Int) {
        var dismiss = false
        val snackBar = Snackbar
            .make(requireView(), "", Snackbar.LENGTH_SHORT)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            .setAction("Deshacer...") {
                listAdapter.addItemAt(temporal, pos)
                dismiss = true

            }


            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onShown(transientBottomBar: Snackbar?) {
                    super.onShown(transientBottomBar)

                }

                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)

                    if (!dismiss) {
                        try {
                            deleteFromDb(temporal)
                        } catch (e: Exception) {
                            Log.d(XX, "Exception by agony")
                        }

                    }


                }
            })

        val snackBarView = snackBar.view

        snackBarView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.Crimson))
        snackBar.show()

    }

    private fun deleteFromDb(temporal: PetBase) {
        val name = temporal.name
        viewModel.deletePet(temporal).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Succes -> {
                    makeToast(requireContext(), "$name ha sido eliminado de la lista")
                }
                is Result.Failure -> {
                    Log.d(XX, it.exception.toString())
                }
                is Result.Loading -> {

                }
            }
        }
    }

    private fun adapterMethod(view: View, pet: PetBase, i: Int) {
       val action=LandingFragmentDirections.actionLandingFragmentToPetDetailFragment(pet)
        findNavController().navigate(action)
    }

    private fun listenerNewPet() {
        binding.addButton.setOnClickListener {
            showDialog(requireContext(), layoutInflater, findNavController())
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