package com.wikilift.tfg.ui.creationpet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.navArgs
import com.wikilift.tfg.R
import com.wikilift.tfg.core.extensions.makeSnack
import com.wikilift.tfg.core.extensions.selected
import com.wikilift.tfg.databinding.FragmentPetCreationPetBinding
import com.wikilift.tfg.ui.creationpet.adapter.CustomDropDownAdapter


class PetCreationPetFragment : Fragment(R.layout.fragment_pet_creation_pet) {

    private lateinit var binding:FragmentPetCreationPetBinding
    private  val args:PetCreationPetFragmentArgs by navArgs()
    private lateinit var petName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        petName= args.petName





    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentPetCreationPetBinding.bind(view)
        binding.petname.text=petName



        val customDropDownAdapterType=CustomDropDownAdapter(requireContext(), resources.getStringArray(R.array.animal_types).toList())
        binding.typeAnimal.adapter=customDropDownAdapterType
        binding.typeAnimal.selected { makeSnack(requireView(),it.toString()) }






    }
}