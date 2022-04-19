package com.wikilift.tfg


import android.os.Bundle
import android.view.MenuItem

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment


import com.wikilift.tfg.core.extensions.IOnBackPressed
import com.wikilift.tfg.core.extensions.hide
import com.wikilift.tfg.core.extensions.show
import com.wikilift.tfg.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var toggle: ActionBarDrawerToggle


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




        toggle = ActionBarDrawerToggle(
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

        observeDestinationChange()



    }




    private fun setupDrawerLayout() {
        // binding.navView.setupWithNavController(navController)
        binding.navView.setNavigationItemSelectedListener { menuItem: MenuItem? ->
            //write your implementation here
            //to close the navigation drawer
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                when (menuItem?.itemId) {
                    R.id.home -> {
                        navController.navigate(R.id.landingFragment)

                    }


                    R.id.addPet -> {
                        com.wikilift.tfg.core.uiutils.showDialog(
                            this,
                            layoutInflater,
                            navController
                        )
                    }


                }
                binding.drawerLayout.closeDrawer(GravityCompat.START)

            }

            false

        }


    }


    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
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


                else -> {
                    binding.toolbar.hide()
                }
            }
        }
    }


}