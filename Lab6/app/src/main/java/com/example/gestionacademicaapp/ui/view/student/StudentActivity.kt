package com.example.gestionacademicaapp.ui.view.student

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.gestionacademicaapp.R
import com.example.gestionacademicaapp.databinding.ActivityStudentBinding
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import kotlinx.android.synthetic.main.activity_student.*
import kotlinx.android.synthetic.main.nav_fragment_container.*
import kotlinx.android.synthetic.main.nav_fragment_container.view.*

class StudentActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityStudentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.navView.toolbar)

        var toggle  = ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        toolbar.title = "Students"
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, StudentsFragment()).commit()

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_careers -> {
                toolbar.title = "Students"
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, StudentsFragment()).commit()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


}