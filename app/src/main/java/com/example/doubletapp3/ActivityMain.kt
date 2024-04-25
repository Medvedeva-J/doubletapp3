package com.example.doubletapp3

import Constants
import HabitViewModel
import HabitsListViewModel
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.doubletapp3.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.*


open class ActivityMain : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    lateinit var habitsListVM: HabitsListViewModel
    lateinit var habitVM: HabitViewModel
    lateinit var db: HabitsDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout
        setSupportActionBar(binding.include.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_menu_24)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, FragmentHome()).commit()
        navigationView.setCheckedItem(navigationView.menu.findItem(R.id.home))

        db = HabitsDB.getDB(this)
        val allHabits = db.habitsDao().getAll()
        if (navigationView.checkedItem?.itemId == R.id.home)

        allHabits.observe(this@ActivityMain, Observer {
            habitsListVM.habitsList.value = it as MutableList<Habit>
            habitsListVM.update()
        })

        habitsListVM = ViewModelProvider(this, object: ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HabitsListViewModel() as T
            }
        }).get(HabitsListViewModel::class.java)


        habitVM = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HabitViewModel(null) as T
            }
        }).get(HabitViewModel::class.java)
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
        val a = supportFragmentManager.beginTransaction()
        val bundle: Bundle = Bundle()
        bundle.putInt(Constants.KEY_REQUEST_CODE, Constants.REQUEST_HABIT_CREATED)
        val fragment = FragmentHabitInfo()
        fragment.arguments = bundle
        a.replace(R.id.main_container, fragment)
        a.addToBackStack(null).commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e("AAA", "onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.e("AAA", "onRestoreInstanceState")
    }
}