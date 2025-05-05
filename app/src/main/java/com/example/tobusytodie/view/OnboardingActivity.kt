package com.example.tobusytodie.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.tobusytodie.databinding.ActivityOnboardingBinding
import com.example.tobusytodie.utils.FragmentCommunicator
import com.example.tobusytodie.R
import android.view.View
import androidx.navigation.ui.navigateUp

class OnboardingActivity : AppCompatActivity(), FragmentCommunicator{

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var biding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(biding.root)
    }

    override fun showLoader(value: Boolean) {
        biding.loaderContainerView.visibility = if (value) View.VISIBLE else View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}