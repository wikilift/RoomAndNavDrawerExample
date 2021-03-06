package com.wikilift.tfg.ui.testingZone

import android.animation.*
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView

import com.wikilift.tfg.R
import com.wikilift.tfg.core.extensions.IOnBackPressed
import com.wikilift.tfg.core.extensions.disableViewDuringAnimation
import com.wikilift.tfg.databinding.FragmentTestZoneBinding


class TestZone : Fragment(R.layout.fragment_test_zone),IOnBackPressed{
    private lateinit var binding: FragmentTestZoneBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTestZoneBinding.bind(view)

        setListeners()
    }

    private fun setListeners() {
        binding.rotateButton.setOnClickListener {
            rotater()
        }
        binding.translateButton.setOnClickListener {
            translater()
        }

        binding.scaleButton.setOnClickListener {
            scaler()
        }

        binding.fadeButton.setOnClickListener {
            fader()
        }

        binding.colorizeButton.setOnClickListener {
            colorizer()
        }

        binding.showerButton.setOnClickListener {
            shower()
        }
    }

    private fun shower() {
        val container = binding.star.parent as ViewGroup        //creamos contenedor
        val containerW = container.width                        //adquirimos el ancho del layout
        val containerH = container.height                        //adquirimos el alto del layout
        var starW: Float = binding.star.width.toFloat()          //adquirimos el ancho de la estrella
        var starH: Float = binding.star.height.toFloat()        //adquirimos el alto de la estrella
        val newStar = AppCompatImageView(requireContext())      //a??adimos programaticamente otra estrella
        newStar.setImageResource(R.drawable.ic_star)
        newStar.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT)
        container.addView(newStar)
        newStar.scaleX = Math.random().toFloat() * 1.5f + .1f       //damos posici??n y tama??o aleatorio entre 1,5 y 1 a la estrella creada
        newStar.scaleY = newStar.scaleX
        starW *= newStar.scaleX
        starH *= newStar.scaleY
        newStar.translationX = Math.random().toFloat() *            //posici??n aleatoria en el eje x
                containerW - starW / 2

        //creaci??n de dos propiedades a una animaci??n con interpolaci??n
        val mover = ObjectAnimator.ofFloat(newStar, View.TRANSLATION_Y,
            -starH, containerH + starH)
        mover.interpolator = AccelerateInterpolator(1f)         //ca??da, cada vez m??s deprisa por gravedad
        val rotator = ObjectAnimator.ofFloat(newStar, View.ROTATION,    //rotaci??n, va a ser lineal
            (Math.random() * 1080).toFloat())
        rotator.interpolator = LinearInterpolator()


        //a continuaci??n a??adimos un set de animaciones en la que decimos que se ejecutar??n juntas y una duraci??n random para dar sensaci??n de diferencia
        val set = AnimatorSet()
        set.playTogether(mover, rotator)
        set.duration = (Math.random() * 1500 + 500).toLong()
        //a??adiremos un listener para que cuando termine la animaci??n elimine las estrellas que se han a??adido
        set.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                container.removeView(newStar)
            }
        })
        set.start()

    }

    private fun colorizer() {
        var animator = ObjectAnimator.ofArgb(
            binding.frameLayout,
            "backgroundColor", Color.BLACK, Color.RED   //Cambio de un color a otro atacando a una propiedad del xml
        )
        animator.duration=500
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.disableViewDuringAnimation(binding.colorizeButton)
        animator.start()
    }

     fun fader() {
        val animator = ObjectAnimator.ofFloat(binding.star, View.ALPHA, 0f)
        animator.repeatCount = 1
        animator.repeatMode = ObjectAnimator.REVERSE
        animator.duration = 500
        animator.disableViewDuringAnimation(binding.fadeButton)
        animator.start()
    }

     fun scaler() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)//propiedad en el eje X
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)//propiedad en el eje Y
        val animator =
            ObjectAnimator.ofPropertyValuesHolder(binding.star, scaleX, scaleY)//aplicar los valores
        animator.repeatCount = 1 // veces q se repite
        animator.repeatMode = ObjectAnimator.REVERSE // que hace cuando se repite
        animator.disableViewDuringAnimation(binding.scaleButton)//evitamos darle al bot??n
        animator.start()
    }

     fun translater() {
        val animator =
            ObjectAnimator.ofFloat(binding.star, View.TRANSLATION_X, 200f) //inicia movimiento
        animator.repeatCount = 1 //cuantas veces se repita para considerarse completa
        animator.repeatMode =
            ObjectAnimator.REVERSE //como se va a ejecutar cuando haga la repetici??n
        disableViewDuringAnimation(
            binding.translateButton,
            animator
        )//llamada al m??todo que desactiva el bot??n de animaci??n mientras est?? en ejecuci??n
        animator.start()
    }

    private fun rotater() {
        val animator = ObjectAnimator.ofFloat(binding.star, View.ROTATION, -360f, 0f)
        animator.duration = 5000
        animator.disableViewDuringAnimation(binding.rotateButton)
        animator.start()
    }


    private fun disableViewDuringAnimation(
        view: View,
        animator: ObjectAnimator
    ) {
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }

    override fun onBackPressed(): Boolean =true
}