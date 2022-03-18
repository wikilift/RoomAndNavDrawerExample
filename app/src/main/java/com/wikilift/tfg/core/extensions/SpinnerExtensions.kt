package com.wikilift.tfg.core.extensions

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

fun Spinner.selected(action: (position:Int) -> Unit) {
    this.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            action(position)
        }
    }
}