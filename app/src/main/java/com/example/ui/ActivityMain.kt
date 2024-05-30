package com.example.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.doubletapp3.R
import com.example.doubletapp3.databinding.ActivityMainBinding


class ActivityMain : AppCompatActivity() {
    private val imageLink: String = "https://tlum.ru/uploads/ca1c65ffaf112008d3b9697904492b78a168e10ceba237dc512819ece3409afc.jpeg"
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        val havHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val navController = havHostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            navController.graph, binding.drawerLayout
        )
        binding.navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        setContentView(binding.root)
        loadPicture()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) ||
                super.onSupportNavigateUp()
    }

    private fun loadPicture(){
        Glide.with(this)
            .load(imageLink)
            .centerCrop()
            .override(120, 120)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.navView.getHeaderView(0).findViewById(R.id.avatar_pic))
    }
}