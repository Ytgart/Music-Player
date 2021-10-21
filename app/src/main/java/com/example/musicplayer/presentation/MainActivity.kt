package com.example.musicplayer.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.room.Room
import com.example.musicplayer.data.LoginStateRepository
import com.example.musicplayer.R
import com.example.musicplayer.data.database.PlayerDatabase
import com.example.musicplayer.data.database.PlayerUserDao
import com.example.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val activityViewModel: ActivityViewModel by viewModels()
    lateinit var playerUserDao: PlayerUserDao
        private set
    lateinit var loginStateRepository: LoginStateRepository
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginStateRepository = LoginStateRepository(this)

        val db =
            Room.databaseBuilder(
                applicationContext, PlayerDatabase::class.java, "main-database"
            ).allowMainThreadQueries().build()
        playerUserDao = db.playerUserDao()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        activityViewModel.isOnLoginScreen.observe(this, { isOnLoginScreen ->
            if (!isOnLoginScreen) {
                activityViewModel.isOnLoginScreen.postValue(true)
                if (!loginStateRepository.getLoginState()) {
                    navController.navigate(R.id.loginFragment)
                }
            }
        })
    }
}