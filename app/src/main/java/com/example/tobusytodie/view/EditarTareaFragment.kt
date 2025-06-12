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
import androidx.navigation.fragment.navArgs
import com.example.tobusytodie.databinding.FragmentEditarTareaBinding
import com.example.tobusytodie.model.Tarea
import com.example.tobusytodie.viewModel.AgregarViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class EditarTareaFragment : Fragment() {

    private var _binding: FragmentEditarTareaBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AgregarViewModel by viewModels()
    private val args: EditarTareaFragmentArgs by navArgs()

    private lateinit var tareaActual: Tarea

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditarTareaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtenemos la tarea por ID
        val tareaId = args.tareaId
        viewModel.getTareaById(tareaId)

        // Cargamos datos en los campos
        viewModel.selectedTarea.observe(viewLifecycleOwner) { tarea ->
            tarea?.let {
                tareaActual = it
                binding.etNombreTarea.setText(it.name)
                binding.etDescripcionTarea.setText(it.description)

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                binding.etFechaTarea.setText(sdf.format(it.date))
            }
        }

        // DatePicker al hacer click
        binding.etFechaTarea.apply {
            isFocusable = false
            isClickable = true
            inputType = android.text.InputType.TYPE_NULL

            setOnClickListener {
                val calendar = Calendar.getInstance()
                val picker = DatePickerDialog(
                    requireContext(),
                    { _, year, month, dayOfMonth ->
                        val fechaSeleccionada = "%02d/%02d/%d".format(dayOfMonth, month + 1, year)
                        setText(fechaSeleccionada)
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                picker.show()
            }
        }

        // Guardar cambios
        binding.btnGuardarCambios.setOnClickListener {
            val nombre = binding.etNombreTarea.text.toString()
            val descripcion = binding.etDescripcionTarea.text.toString()
            val fechaTexto = binding.etFechaTarea.text.toString()

            if (nombre.isBlank() || descripcion.isBlank() || fechaTexto.isBlank()) {
                Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val fecha = try {
                sdf.parse(fechaTexto)
            } catch (e: Exception) {
                null
            }

            if (fecha == null) {
                Toast.makeText(requireContext(), "Formato de fecha inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tareaEditada = tareaActual.copy(
                name = nombre,
                description = descripcion,
                date = fecha
            )

            viewModel.updateTarea(tareaEditada)
            Toast.makeText(requireContext(), "Tarea actualizada", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        // Desactivar botón si está cargando
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.btnGuardarCambios.isEnabled = !isLoading
        }

        // Mostrar errores
        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            errorMsg?.let {
                Toast.makeText(requireContext(), "Error: $it", Toast.LENGTH_LONG).show()
                viewModel.clearError()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
