package com.wikilift.tfg.ui.creationpet

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.datepicker.MaterialDatePicker
import com.wikilift.tfg.R
import com.wikilift.tfg.core.extensions.*
import com.wikilift.tfg.core.uiutils.Result
import com.wikilift.tfg.data.local.datasource.PetLocalDataSourceImpl
import com.wikilift.tfg.data.local.room.AppDatabase
import com.wikilift.tfg.data.model.room.entity.PetBase

import com.wikilift.tfg.databinding.FragmentPetCreationPetBinding
import com.wikilift.tfg.domain.PetRepoImpl
import com.wikilift.tfg.presentation.PetViewModel
import com.wikilift.tfg.presentation.PetViewModelFactory


class PetCreationPetFragment : Fragment(R.layout.fragment_pet_creation_pet),IOnBackPressed {

    private lateinit var binding: FragmentPetCreationPetBinding
    private val args: PetCreationPetFragmentArgs by navArgs()
    private lateinit var petName: String
    private lateinit var currentPet: PetBase

    private var bitmap: Bitmap? = null

    private val viewModel by viewModels<PetViewModel> {
        PetViewModelFactory(
            PetRepoImpl(
                PetLocalDataSourceImpl(AppDatabase.getDatabase(requireContext()).productDao())
            )
        )
    }


    //register activity to camera launch
    @RequiresApi(Build.VERSION_CODES.P)
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val uri = it.data?.data!!
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(requireActivity().contentResolver, uri)
                bitmap = ImageDecoder.decodeBitmap(source)
                binding.photo.setImageURI(uri)


                val path = MediaStore.Images.Media.insertImage(
                    context?.contentResolver,
                    bitmap,
                    Utils.getNamePicture(),
                    null
                )
                currentPet.imgPath = path



            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        petName = args.petName

        currentPet = PetBase(name = petName)


    }


    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPetCreationPetBinding.bind(view)
        binding.petname.text = petName


        setDropDownsAndPickers()
        btnSaveListener()


    }

    private fun btnSaveListener() {
        binding.btnRegister.setOnClickListener {

            if (currentPet.sexType.isBlank()) {
                currentPet.sexType = "Único"
            }
            if (currentPet.type.isBlank()) {
                currentPet.type = "Único"
            }
            currentPet.race = if (binding.breed.text.toString()
                    .isBlank()
            ) "Única" else binding.breed.text.toString()
            currentPet.weight = if (binding.weight.text.toString()
                    .isBlank()
            ) "0 Kg" else "${binding.weight.text.toString()} Kg"
            if (bitmap == null) {
                makeToast(requireContext(), "No ha insertado ninguna foto")

                return@setOnClickListener
            }
            if (currentPet.haveChip == "Sí") {
                currentPet.chipNumber = binding.edtChipNumber.text.toString()
                if (currentPet.chipNumber.isEmpty()) {

                    makeToast(requireContext(), "Debe introducir el número de chip")
                    return@setOnClickListener
                }
            }
            viewModel.insertPet(currentPet).observe(viewLifecycleOwner){result->
                when(result){
                    is Result.Loading->{binding.progressbar.show()}
                    is Result.Succes->{
                        binding.progressbar.hide()
                        makeToast(requireContext(),"${currentPet.name} se ha registrado correctamente")
                        findNavController().navigate(R.id.action_petCreationPet_to_landingFragment)}
                    is Result.Failure->{Log.d(XX,result.exception.toString())}
                }

            }


        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("ClickableViewAccessibility")
    private fun setDropDownsAndPickers() {
        val genderAdapter =
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.gender,
                R.layout.custom_drop_down_adapter
            )
        binding.genderDrop.setAdapter(genderAdapter)
        val typeAdapter =
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.animal_types,
                R.layout.custom_drop_down_adapter
            )
        binding.typeDrop.setAdapter(typeAdapter)
        val chipAdapter =
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.yesNo,
                R.layout.custom_drop_down_adapter
            )
        binding.hasChip.setAdapter(chipAdapter)


        binding.date.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    val picker =
                        MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Select date")
                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                            .build()
                    picker.addOnPositiveButtonClickListener {
                        binding.date.setText(picker.headerText)
                        currentPet.birthDate = picker.headerText.toString()
                    }

                    picker.show(requireActivity().supportFragmentManager, "DatePicker")


                }

            }
            true
        }

        binding.photo.setOnClickListener {
            try {
                ImagePicker.with(requireActivity()).createIntentFromDialog { launcher.launch(it) }

                //findNavController().navigate(R.id.action_landingFragment_to_petDetailFragment)

            } catch (e: Exception) {
                Log.d(XX, e.toString())
            }
        }

        binding.hasChip.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position).toString()


                if (selectedItem == "Sí") {
                    binding.chipNumber.show()
                    currentPet.haveChip = "Sí"
                } else {
                    binding.chipNumber.hide()
                    currentPet.haveChip = "No"
                }
            }

        binding.genderDrop.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position).toString()


                currentPet.sexType = selectedItem
            }

        binding.typeDrop.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position).toString()


                currentPet.type = selectedItem
            }

    }

    override fun onBackPressed(): Boolean =true
}