package com.wikilift.tfg.ui.petDetail


import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment

import android.view.View



import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs


import com.wikilift.tfg.R
import com.wikilift.tfg.core.TAG
import com.wikilift.tfg.core.extensions.IOnBackPressed
import com.wikilift.tfg.databinding.FragmentPetDetailBinding


class PetDetailFragment : Fragment(R.layout.fragment_pet_detail),IOnBackPressed{

    private lateinit var binding: FragmentPetDetailBinding




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentPetDetailBinding.bind(view)

        binding.btnDetail.setOnClickListener{

            Log.d(TAG,"pene")
           //findNavController().navigate(R.id.action_petDetailFragment_to_landingFragment)

        }



    }

    override fun onBackPressed(): Boolean = true

}