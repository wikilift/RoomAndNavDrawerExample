package com.wikilift.tfg.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.wikilift.tfg.R
import com.wikilift.tfg.core.extensions.hide
import com.wikilift.tfg.core.extensions.rotate
import com.wikilift.tfg.databinding.FragmentSplashBinding


class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var binding:FragmentSplashBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentSplashBinding.bind(view)

        val k = activity?.findViewById<View>(R.id.toolbar)
        k?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_splashFragment3_to_landingFragment)
        }, 4000)
    }
}