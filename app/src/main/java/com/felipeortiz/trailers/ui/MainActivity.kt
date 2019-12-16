package com.felipeortiz.trailers.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.updatePadding
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.felipeortiz.trailers.R
import com.felipeortiz.trailers.internal.doOnApplyWindowInsets
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val KEY_PREF_THEME = "theme"

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Adjusts insets for edge to edge
        toolbar.doOnApplyWindowInsets { view, windowInsets, initialPadding ->
            view.updatePadding(
                top = initialPadding.top + windowInsets.systemWindowInsetTop
            )
        }

//        this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        // Navigation setup
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.playVideoFragment -> {
                    toolbar.visibility = View.GONE
                    bottom_nav.visibility = View.GONE
                }
                R.id.searchFragment -> {
                    bottom_nav.visibility = View.GONE
                }
                else -> {
                    toolbar.visibility = View.VISIBLE
                    bottom_nav.visibility = View.VISIBLE
                }
            }
        }
        // set top level fragments
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.trendingFragment, R.id.discoverFragment, R.id.settingsFragment))
//        collapsing_toolbar_layout.setupWithNavController(toolbar, navController, appBarConfiguration)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        bottom_nav.setupWithNavController(navController)

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
        setTheme()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    private fun setTheme() {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener {sharedPref, key ->
            val themePreference = sharedPref.getInt(key, 2)
            Timber.d("Theme pref: $themePreference")
            when (themePreference) {
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    this.recreate()
                }
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    this.recreate()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    this.recreate()
                }
            }
        }
    }

}
