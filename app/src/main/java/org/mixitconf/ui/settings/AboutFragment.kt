package org.mixitconf.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.mixitconf.BuildConfig
import org.mixitconf.R
import org.mixitconf.databinding.FragmentAboutBinding
import org.mixitconf.ui.BaseFragment

class AboutFragment : BaseFragment<FragmentAboutBinding>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentAboutBinding.inflate(inflater, container, false).let {
            setViewBinding(it)
            viewBinding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBuildNumber()
    }

    private fun initBuildNumber() {
        val currentVersion = BuildConfig.VERSION_NAME
        viewBinding.tvVersion.text = String.format(getString(R.string.settings_about_version), currentVersion)
    }

}