package com.wikilift.tfg.core.extensions

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.wikilift.tfg.R


fun makeToast(context: Context, msg: String) {

    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()


}

fun Fragment.makeSnack(context: View, msg: String, position: String = "") {
    val x = Snackbar
        .make(context, msg, Snackbar.LENGTH_LONG)
        .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

        .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.Crimson))

    if (position == "") {
        x.apply {
            (view.layoutParams as FrameLayout.LayoutParams)//.gravity = Gravity.TOP
        }.show()
    } else {
        when (position) {

            "center" -> {

                x.apply {
                    (view.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.CENTER
                }.show()

            }
            "top" -> {

                x.apply {
                    (view.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.TOP
                }.show()

            }
            "bottom" -> {

                x.apply {
                    (view.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.BOTTOM
                }.show()

            }
        }
    }

}

