package com.wikilift.tfg




import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*

import com.wikilift.tfg.core.extensions.IOnBackPressed
import com.wikilift.tfg.core.extensions.hide
import com.wikilift.tfg.core.extensions.show
import com.wikilift.tfg.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupDrawerLayout()
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)




        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        //supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_pug_4862083)
        observeDestinationChange()
        //setStatusBar()

    }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }



    private fun setupDrawerLayout() {
        binding.navView.setupWithNavController(navController)

    }



    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        }


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

    private fun observeDestinationChange() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.landingFragment -> {
                    binding.toolbar.show()

                }
                R.id.splashFragment -> {
                    binding.toolbar.hide()
                }
                R.id.testZone -> {
                    binding.toolbar.hide()
                }


                else -> {
                    binding.toolbar.show()
                }
            }
        }
    }
 /*   //future implementation
    private fun setStatusBar() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
        )
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }*/

}