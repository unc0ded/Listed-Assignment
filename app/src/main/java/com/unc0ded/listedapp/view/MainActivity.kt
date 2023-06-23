package com.unc0ded.listedapp.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.unc0ded.listedapp.R
import com.unc0ded.listedapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        binding.bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.titleText.text = getString(when (destination.id) {
                R.id.nav_dashboard -> R.string.label_dashboard
                R.id.nav_courses -> R.string.label_courses
                R.id.nav_campaigns -> R.string.label_campaigns
                R.id.nav_profile -> R.string.label_profile
                else -> R.string.app_name
            })
        }
    }
}