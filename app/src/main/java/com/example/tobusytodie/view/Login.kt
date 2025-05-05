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
import com.example.tobusytodie.databinding.FragmentLoginBinding
import com.example.tobusytodie.utils.FragmentCommunicator
import com.example.tobusytodie.viewModel.LoginViewModel
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.tobusytodie.ListActivity


class Login : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
   private val viewModel by viewModels<LoginViewModel>()
    var isValid : Boolean = false
    private lateinit var communicator: FragmentCommunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        communicator = requireActivity() as OnboardingActivity
        setupView()
        setupObservers()
        return binding.root
    }


    private fun setupView(){

        //cuando de click en el boton de login
        binding.botonIngresar.setOnClickListener {

            if(isValid){
                requestLogin()
            }else{
                Toast.makeText(activity, "Datos Invalidos ", Toast.LENGTH_SHORT).show()
            }
        }
        binding.emailTIET.addTextChangedListener{
            if(binding.emailTIET.text.toString().isEmpty()){
                binding.emailTIET.error = "Campo requerido"
                isValid = false
            }else{
                isValid = true
            }
        }

        binding.passwordTIET.addTextChangedListener{
            if (binding.passwordTIET.text.toString().isEmpty()) {
                binding.passwordTIET.error = "Campo requerido"
                isValid = false
            } else {
                isValid = true
            }
        }

        binding.botonRegistrarDeLogin.setOnClickListener {
            findNavController().navigate(R.id.action_login2_to_register2)
        }


    }
    private fun setupObservers(){
        viewModel.loaderState.observe(viewLifecycleOwner){ loaderState ->
            communicator.showLoader(loaderState)//llamamos al loader para mostrarlo
        }
        //accedemos a los publisher y con el observer definimos quien es el encargado del ciclo de vida
        viewModel.sessionValid.observe(viewLifecycleOwner) { sessionValid ->
            if (sessionValid) {//si el usuario existe

                //llamamos a la actividad principal
                val intent = Intent(activity, ListActivity::class.java)//llamamos a la actividad para inciar nuevo flujo
                findNavController().navigate(R.id.action_login2_to_firstFragment)
                startActivity(intent)
                activity?.finish()
            }else{
                //mandamos mensaje
                Toast.makeText(activity, "Ingreso invalido", Toast.LENGTH_SHORT).show()
            }

        }
    }
    private fun requestLogin() { //aqui le mandamos a la funcion  del viewModel los datos que ingresaron
        viewModel.requestSingnIn(binding.emailTIET.text.toString(),
            binding.passwordTIET.text.toString())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}