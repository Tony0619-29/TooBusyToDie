package com.example.tobusytodie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tobusytodie.databinding.FragmentSecondBinding
import com.example.tobusytodie.utils.FragmentCommunicator


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    var isValid : Boolean = false
    private lateinit var communicator: FragmentCommunicator


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        communicator = requireActivity() as MainActivity
        return binding.root

    }






}