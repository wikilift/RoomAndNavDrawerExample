package com.wikilift.tfg.ui.petDetail


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
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels


import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.datepicker.MaterialDatePicker


import com.wikilift.tfg.R
import com.wikilift.tfg.core.TAG
import com.wikilift.tfg.core.extensions.*
import com.wikilift.tfg.core.uiutils.Result
import com.wikilift.tfg.data.local.datasource.PetLocalDataSourceImpl
import com.wikilift.tfg.data.local.room.AppDatabase
import com.wikilift.tfg.data.model.room.entity.PetBase
import com.wikilift.tfg.databinding.FragmentPetDetailBinding
import com.wikilift.tfg.domain.PetRepoImpl
import com.wikilift.tfg.presentation.PetViewModel
import com.wikilift.tfg.presentation.PetViewModelFactory


class PetDetailFragment : Fragment(R.layout.fragment_pet_detail), IOnBackPressed {

    private lateinit var binding: FragmentPetDetailBinding

    private val args: PetDetailFragmentArgs by navArgs()
    private lateinit var travelDto: PetBase

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
                travelDto.imgPath = path


            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        travelDto = args.travelDto
    }


    @RequiresApi(Build.VERSION_CODES.P)
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPetDetailBinding.bind(view)

        try {




            binding.petname.text = travelDto.name
            binding.sex.setText(travelDto.sexType)
            binding.type.setText(travelDto.type)
            binding.breed.setText(travelDto.race)
            binding.date.setText(travelDto.birthDate)
            binding.weight.setText(travelDto.weight)
            if (travelDto.haveChip == "Sí") {
                binding.hasChip.setText("Sí")
                binding.edtChipNumber.setText(travelDto.chipNumber)
                binding.chipNumber.show()
            } else {
                binding.hasChip.setText("No")
            }
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
                            travelDto.birthDate = picker.headerText.toString()
                        }

                        picker.show(requireActivity().supportFragmentManager, "DatePicker")


                    }

                }
                true
            }
            binding.hasChip.onItemClickListener =
                AdapterView.OnItemClickListener { parent, _, position, _ ->
                    val selectedItem = parent.getItemAtPosition(position).toString()


                    if (selectedItem == "Sí") {
                        binding.chipNumber.show()
                        travelDto.haveChip = "Sí"
                    } else {
                        binding.chipNumber.hide()
                        travelDto.haveChip = "No"
                    }
                }
            Glide.with(requireContext()).load(travelDto.imgPath).centerCrop().into(binding.photo)
        } catch (e: Exception) {

        }

        binding.photo.setOnClickListener {
            try {
                ImagePicker.with(requireActivity()).createIntentFromDialog { launcher.launch(it) }



            } catch (e: Exception) {
                Log.d(XX, e.toString())
            }
        }
        binding.btnRegister.setOnClickListener{
            travelDto.weight = if (binding.weight.text.toString()
                    .isBlank()
            ) travelDto.weight else "${binding.weight.text.toString()} Kg"
            viewModel.updatePet(travelDto).observe(viewLifecycleOwner){
                when(it){
                    is Result.Loading->{binding.progressbar.show()}
                    is Result.Succes->{
                        binding.progressbar.hide()
                        makeToast(requireContext(),"${travelDto.name} ha sido actualizado")
                        findNavController().navigate(R.id.action_petDetailFragment_to_landingFragment)
                    }
                    is Result.Failure->{
                        Log.d(XX,it.exception.toString())
                        binding.progressbar.hide()
                        makeToast(requireContext(),"Algo salió mal")
                        findNavController().navigate(R.id.action_petDetailFragment_to_landingFragment)
                    }

                }
            }
        }

    }

    override fun onBackPressed(): Boolean = true

}