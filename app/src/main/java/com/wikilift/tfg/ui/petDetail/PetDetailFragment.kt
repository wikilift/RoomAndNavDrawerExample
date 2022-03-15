package com.wikilift.tfg.ui.petDetail

import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle

import androidx.fragment.app.Fragment

import android.view.View
import androidx.appcompat.widget.Toolbar


import androidx.navigation.fragment.findNavController
import com.wikilift.tfg.MainActivity

import com.wikilift.tfg.R
import com.wikilift.tfg.databinding.FragmentPetDetailBinding


class PetDetailFragment : Fragment(R.layout.fragment_pet_detail) {

    private lateinit var binding: FragmentPetDetailBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentPetDetailBinding.bind(view)

        binding.btnDetail.setOnClickListener{
            findNavController().navigate(R.id.action_petDetailFragment_to_landingFragment)
        }



    }

}