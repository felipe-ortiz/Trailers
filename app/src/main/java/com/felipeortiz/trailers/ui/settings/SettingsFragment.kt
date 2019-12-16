package com.felipeortiz.trailers.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.felipeortiz.trailers.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}