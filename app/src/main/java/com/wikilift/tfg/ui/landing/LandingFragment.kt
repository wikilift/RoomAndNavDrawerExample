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
import com.wikilift.tfg.data.local.datasource.PetLocalDataSourceImpl
import com.wikilift.tfg.data.local.room.AppDatabase
import com.wikilift.tfg.databinding.FragmentLandingBinding
import com.wikilift.tfg.domain.PetRepoImpl
import com.wikilift.tfg.presentation.PetViewModel
import com.wikilift.tfg.presentation.PetViewModelFactory


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
        if (firstInit) {
            findNavController().navigate(R.id.action_landingFragment_to_splashFragment3)
            firstInit = false
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
                    Log.d(XX, "He acabado ${result.data.size}")
                }
                is Result.Failure -> {
                    Log.d(XX, result.exception.toString())
                }
            }
        }
    }

    private fun listenerNewPet() {
        binding.addButton.setOnClickListener {
            showDialog()
        }
    }

    @SuppressLint("InflateParams")
    private fun showDialog() {


        val builder = AlertDialog.Builder(requireContext())
        val inflater: LayoutInflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.modal_add_pet, null)
        val btnAccept: MaterialButton = dialogLayout.findViewById(R.id.btn_accept)
        val btnCancel: MaterialButton = dialogLayout.findViewById(R.id.btn_cancel)
        val editText: EditText = dialogLayout.findViewById(R.id.nameOfPet)

        var inputName: String
        var alertDialog: AlertDialog? = null

        alertDialog = with(builder) {
            builder.setCancelable(true)
            btnAccept.setOnClickListener {
                inputName = editText.text.toString()
                if (inputName.isNotEmpty()) {
                    val action = LandingFragmentDirections.actionLandingFragmentToPetCreationPet(inputName)
                    findNavController().navigate(action)
                    alertDialog?.dismiss()

                } else {
                    editText.requestFocus()
                    editText.error="Debe tener un nombre"

                }

            }
            btnCancel.setOnClickListener {

                alertDialog?.dismiss()
            }
            setView(dialogLayout)


        }.show()


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