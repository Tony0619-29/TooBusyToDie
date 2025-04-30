package com.example.tobusytodie.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tobusytodie.databinding.ActivityMainBinding
import com.example.tobusytodie.utils.FragmentCommunicator

class MainActivity : AppCompatActivity(), FragmentCommunicator {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun showLoader(value: Boolean) {
        binding.loaderContainerView.visibility = if (value) View.VISIBLE else View.GONE
    }
}