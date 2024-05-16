package com.example.doubletapp3

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.bumptech.glide.Glide
import com.example.doubletapp3.databinding.ActivityMainBinding


class ActivityMain : AppCompatActivity() {
    private val imageLink: String = "https://tlum.ru/uploads/ca1c65ffaf112008d3b9697904492b78a168e10ceba237dc512819ece3409afc.jpeg"
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var habitsListVM: HabitsListViewModel
    private lateinit var habitVM: HabitViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        drawerLayout = binding.drawerLayout
        val hostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navController = hostFragment.navController

        appBarConfiguration = AppBarConfiguration(
            navController.graph, binding.drawerLayout
        )
        binding.navView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        setContentView(binding.root)

        HabitsDB.getInstance().habitsDao().getAll().observe(this@ActivityMain, Observer {
            habitsListVM.updateAllHabits(it)
            habitsListVM.update()
        })

        habitsListVM = ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HabitsListViewModel() as T
            }
        })[HabitsListViewModel::class.java]

        habitVM = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HabitViewModel() as T
            }
        })[HabitViewModel::class.java]

        loadPicture()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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