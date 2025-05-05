package com.example.tobusytodie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

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
        communicator = requireActivity() as OnboardingActivity
        setupView()
        return binding.root

    }


    private fun setupView() {
        binding.flechaLogin.setOnClickListener {
            findNavController().navigate(R.id.action_register2_to_login2)
        }

        binding.buttonRegistrar.setOnClickListener {
            viewModel.requestSignUp(
                binding.tietEmail.text.toString(),
                binding.tietPassword.text.toString()
            )
        }

        binding.tietNombre.addTextChangedListener {
            if (binding.tietNombre.text.toString().isEmpty()) {
                binding.txtNombreRegister.error = "Por favor introduce un nombre"
                isValid = false
            } else {
                isValid = true
            }
        }
            //valida que se ingresen los datos
        binding.tietEmail.addTextChangedListener {
                if (binding.tietEmail.text.toString().isEmpty()) {
                    binding.txtCorreoRegister.error = "Por favor introduce un correo"
                    isValid = false
                } else {
                    isValid = true
                }
            }

        binding.tietPassword.addTextChangedListener {
                if (binding.tietPassword.text.toString().isEmpty()) {
                    binding.txtContrasenaRegister.error = "Por favor introduce un correo"
                    isValid = false
                } else {
                    isValid = true
                }
            }
        setupObservers()
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
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}