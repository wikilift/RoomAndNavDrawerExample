package com.wikilift.tfg.ui.landing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.wikilift.tfg.R
import com.wikilift.tfg.core.extensions.makeToast
import com.wikilift.tfg.databinding.FragmentLandingBinding


class LandingFragment : Fragment(R.layout.fragment_landing) {

    private lateinit var  binding: FragmentLandingBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding=FragmentLandingBinding.bind(view)

        binding.btnLanding.setOnClickListener{ e->
            findNavController().navigate(R.id.action_landingFragment_to_petDetailFragment)
            makeToast(requireContext(),"voy a detail por la derecha")
        }
    }
}