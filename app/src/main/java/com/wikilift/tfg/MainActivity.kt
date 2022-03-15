package com.wikilift.tfg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wikilift.tfg.core.extensions.IOnBackPressed

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        navHost?.let { navFragment ->
            navFragment.childFragmentManager.primaryNavigationFragment?.let { fragment ->
                (fragment as? IOnBackPressed)?.onBackPressed()?.not()?.let { isCanceled: Boolean ->

                    if (!isCanceled) {
                        super.onBackPressed()
                    }
                }
            }
        }
    }
}