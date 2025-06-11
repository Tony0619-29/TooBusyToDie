package com.example.tobusytodie.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tobusytodie.R
import com.example.tobusytodie.databinding.FragmentPendientesBinding
import com.example.tobusytodie.view.adapters.TareasAdapter
import com.example.tobusytodie.viewModel.AgregarViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PendientesFragment : Fragment() {

    private var _binding: FragmentPendientesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AgregarViewModel by viewModels()
    private lateinit var tareasAdapter: TareasAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPendientesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el adaptador
        tareasAdapter = TareasAdapter(
            tareas = mutableListOf(),
            onClick = { tarea -> /* Acción si se desea usar */ },
            onDelete = { tarea -> viewModel.deleteTarea(tarea.id) },
            onEdit = { tarea ->
                val action = PendientesFragmentDirections
                    .actionPendientesFragmentToEditarTarea(tarea.id)
                findNavController().navigate(action)

            }
        )

        // Configura el RecyclerView
        binding.vrTareas.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tareasAdapter
        }

        // Botón para agregar nueva tarea
        binding.btnAgregar.setOnClickListener {
            findNavController().navigate(R.id.action_pendientesFragment_to_agregarFragment)
        }

        // Observa las tareas desde el ViewModel
        viewModel.tareas.observe(viewLifecycleOwner) { lista ->
            tareasAdapter.addTareas(lista)
        }

        // Cargar tareas
        viewModel.getTareas()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}