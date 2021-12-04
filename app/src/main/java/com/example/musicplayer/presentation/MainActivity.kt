package com.example.musicplayer.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val loginViewModel by viewModel<LoginViewModel>()
    val playerViewModel by viewModel<PlayerViewModel>()
    lateinit var navController: NavController
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHostFragment.navController

        if (!loginViewModel.loginStateRepository.isLogged()) {
            navController.navigate(R.id.loginFragment)
        }

        binding.navigationMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> {
                    if (navController.currentDestination?.id != R.id.mainScreenFragment) {
                        navController.navigate(R.id.mainScreenFragment)
                    }
                    true
                }
                R.id.page_2 -> {
                    if (navController.currentDestination?.id != R.id.searchFragment) {
                        navController.navigate(R.id.searchFragment)
                    }
                    true
                }
                R.id.page_3 -> {
                    if (navController.currentDestination?.id != R.id.favouritesFragment) {
                        navController.navigate(R.id.favouritesFragment)
                    }
                    true
                }
                else -> false
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.playerFragment ||
                destination.id == R.id.loginFragment ||
                destination.id == R.id.registerFragment
            ) {
                binding.navigationMenu.visibility = View.GONE
            } else if (binding.navigationMenu.visibility != View.VISIBLE) {
                binding.navigationMenu.visibility = View.VISIBLE
            }

            when (destination.id) {
                R.id.mainScreenFragment -> binding.navigationMenu.menu
                    .getItem(0).isChecked = true
                R.id.searchFragment -> binding.navigationMenu.menu
                    .getItem(1).isChecked = true
                R.id.favouritesFragment -> binding.navigationMenu.menu
                    .getItem(2).isChecked = true
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        playerViewModel.resetPlayer()
    }
}