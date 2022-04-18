package com.wikilift.tfg.core.uiutils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import androidx.navigation.NavController
import com.google.android.material.button.MaterialButton
import com.wikilift.tfg.R
import com.wikilift.tfg.ui.landing.LandingFragmentDirections

 fun showDialog(context:Context,layoutInflater:LayoutInflater,navController: NavController) {


    val builder = AlertDialog.Builder(context)
    val inflater: LayoutInflater = layoutInflater
    val dialogLayout = inflater.inflate(R.layout.modal_add_pet, null)
    val btnAccept: MaterialButton = dialogLayout.findViewById(R.id.btn_accept)
    val btnCancel: MaterialButton = dialogLayout.findViewById(R.id.btn_cancel)
    val editText: EditText = dialogLayout.findViewById(R.id.nameOfPet)

    var inputName: String
    var alertDialog: AlertDialog? = null

    alertDialog = with(builder) {
        builder.setCancelable(true)
        btnAccept.setOnClickListener {
            inputName = editText.text.toString()
            if (inputName.isNotEmpty()) {
                val action = LandingFragmentDirections.actionLandingFragmentToPetCreationPet(inputName)

                navController.navigate(action)
                alertDialog?.dismiss()

            } else {
                editText.requestFocus()
                editText.error="Debe tener un nombre"

            }

        }
        btnCancel.setOnClickListener {

            alertDialog?.dismiss()
        }
        setView(dialogLayout)


    }.show()


}