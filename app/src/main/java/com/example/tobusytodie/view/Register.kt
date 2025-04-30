package com.example.tobusytodie.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.tobusytodie.R
import com.example.tobusytodie.utils.FragmentCommunicator
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tobusytodie.databinding.FragmentRegisterBinding
import com.example.tobusytodie.viewModel.RegisterViewModel

class Register : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val viewModel by viewModels<RegisterViewModel>()
    var isValid: Boolean = false
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        communicator = requireActivity() as MainActivity
        setupView()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.flechaLogin.setOnClickListener {
            findNavController().navigate(R.id.action_register2_to_login2)
        }

    }




    private fun setupView() {
        binding.flechaLogin.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.buttonRegistrar.setOnClickListener {
            viewModel.requestSignUp(
                binding.tietEmail.text.toString(),
                binding.tietPassword.text.toString()
            )
        }


        //valida que se ingresen los datos
        binding.tietEmail.addTextChangedListener {
            if (binding.tietEmail.text.toString().isEmpty()) {
                binding.textInputLayout4.error = "Por favor introduce un correo"
                isValid = false
            } else {
                isValid = true
            }
        }

        binding.tietPassword.addTextChangedListener {
            if (binding.tietPassword.text.toString().isEmpty()) {
                binding.textInputLayout5.error = "Por favor introduce un correo"
                isValid = false
            } else {
                isValid = true
            }
        }

    }
    private fun setupObservers() {
        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }

        viewModel.validRegister.observe(viewLifecycleOwner) { validRegister ->
            if (validRegister) {
                findNavController().navigate(R.id.action_register2_to_login2)
            }
        }
    }

}