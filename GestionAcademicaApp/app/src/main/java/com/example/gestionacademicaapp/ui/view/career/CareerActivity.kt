package com.example.gestionacademicaapp.ui.view.career

import android.app.ActionBar
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gestionacademicaapp.R
import com.example.gestionacademicaapp.databinding.ActivityCareerBinding
import com.example.gestionacademicaapp.ui.viewmodel.CareerViewModel
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import kotlinx.android.synthetic.main.activity_career.*
import kotlinx.android.synthetic.main.nav_fragment_container.*
import kotlinx.android.synthetic.main.nav_fragment_container.view.*

class CareerActivity : AppCompatActivity(), OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityCareerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCareerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.navView.toolbar)

        var toggle  = ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        toolbar.title = "Careers"
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, CareersFragment()).commit()

    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_item_careers -> {
                toolbar.title = "Careers"
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, CareersFragment()).commit()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }


}