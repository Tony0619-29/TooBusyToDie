package com.example.tobusytodie.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tobusytodie.R
import com.example.tobusytodie.databinding.FragmentAgregarBinding
import com.example.tobusytodie.model.Tarea
import com.example.tobusytodie.viewModel.AgregarViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AgregarFragment : Fragment() {

    private var _binding: FragmentAgregarBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AgregarViewModel by viewModels()
    private val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgregarBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
        // Selector de fecha
        binding.ingFecha.apply {
            isFocusable = false
            isClickable = true
        }

        binding.ingFecha.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), { _, y, m, d ->
                val selectedDate = String.format("%02d/%02d/%04d", d, m + 1, y)
                binding.ingFecha.setText(selectedDate)
            }, year, month, day)

            datePicker.show()
        }

        // Botón regresar
        binding.botonRegresar.setOnClickListener {
            findNavController().navigate(R.id.action_agregarFragment_to_pendientesFragment)
        }

        // Botón agregar tarea
        binding.botonAgregar.setOnClickListener {
            val name = binding.ingNombre.text.toString().trim()
            val description = binding.ingDescripcion.text.toString().trim()
            val dateStr = binding.ingFecha.text.toString().trim()

            if (name.isEmpty() || description.isEmpty() || dateStr.isEmpty()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val date: Date = try {
                format.parse(dateStr) ?: throw IllegalArgumentException("Formato inválido")
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Fecha inválida. Usa el formato dd/MM/yyyy", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tarea = Tarea(
                id = UUID.randomUUID().toString(),
                name = name,
                description = description,
                date = date
            )

            viewModel.createTarea(tarea)

            Toast.makeText(requireContext(), "Tarea creada exitosamente", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_agregarFragment_to_pendientesFragment)
        }

        // Observador de errores
        viewModel.error.observe(viewLifecycleOwner) { error ->
            error?.let {
                Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_SHORT).show()
                viewModel.clearError()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
