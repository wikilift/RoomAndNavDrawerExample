package com.wikilift.tfg.core.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.view.View
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine

fun View.hide(){
    this.visibility=View.GONE
}
fun View.show(){
    this.visibility=View.VISIBLE
}

//animations
var firstInit=true

fun View.rotate(view:View,times:Int=0,mode:Int=0, viewToShow: View? = null,viewToHide:View?=null) {
    val animator = ObjectAnimator.ofFloat(view, View.ROTATION, -360f, 360f)
    animator.duration = 1800
    if(times>0){

        animator.repeatCount = times
       // animator.repeatMode = ObjectAnimator.REVERSE
    }

    if (mode!=0){
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                viewToHide?.hide()
                viewToShow?.show()
            }
        })

    }

    animator.start()
}

