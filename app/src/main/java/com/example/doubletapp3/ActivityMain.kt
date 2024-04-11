package com.example.doubletapp3

import HabitsListViewModel
import Constants as const
import android.content.Intent
import android.graphics.ColorSpace.Model
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.doubletapp3.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class ActivityMain : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    public lateinit var viewModel: HabitsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HabitsListViewModel() as T
            }
        }).get(HabitsListViewModel::class.java)
        drawerLayout = binding.drawerLayout
        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_menu_24)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, FragmentHome()).commit()
        navigationView.setCheckedItem(R.id.home)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.include.toolbar.title = item.title
        when (item.itemId) {
            R.id.home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, FragmentHome()).commit()
            }
            R.id.about -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, FragmentAbout()).commit()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
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
    fun goToHabitCreation(view: View) {
        val sendIntent = Intent(this, ActivityCreateHabit::class.java
        ).apply { putExtra(const.KEY_REQUEST_CODE, const.REQUEST_HABIT_CREATED)}
        val fragment = supportFragmentManager.findFragmentByTag("f" + findViewById<ViewPager2>(R.id.view_pager).currentItem) as FragmentHabitsList
        fragment.startActivityForResult(sendIntent, const.REQUEST_HABIT_CREATED)
    }
}