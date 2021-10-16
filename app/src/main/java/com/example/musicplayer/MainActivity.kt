package com.example.musicplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.room.Room
import com.example.musicplayer.database.PlayerDatabase
import com.example.musicplayer.database.PlayerUserDao
import com.example.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var loginManager: LoginManager
    private lateinit var playerUserDao: PlayerUserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginManager = LoginManager(this)

        val db =
            Room.databaseBuilder(
                applicationContext, PlayerDatabase::class.java, "main-database"
            ).allowMainThreadQueries().build()

        playerUserDao = db.playerUserDao()

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        val navController = navHostFragment.navController

        if (!loginManager.isLogged()) {
            navController.navigate(R.id.loginFragment)
        }
    }

    fun getPlayerUserDao(): PlayerUserDao {
        return playerUserDao
    }
}