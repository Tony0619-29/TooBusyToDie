package com.example.tobusytodie.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tobusytodie.databinding.FragmentItemTareasBinding
import com.example.tobusytodie.model.Tarea
import java.text.SimpleDateFormat
import java.util.Locale

class TareasAdapter (
    private val tareas: MutableList<Tarea>,
    private val onClick: (Tarea) -> Unit,
    private val onDelete: (Tarea) -> Unit,
    private val onEdit: (Tarea) -> Unit
) : RecyclerView.Adapter<TareasAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: FragmentItemTareasBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tarea: Tarea) {
            binding.tvTituloPrincipal.text = tarea.name
            binding.tvDescripcion.text = tarea.description
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            binding.tvFecha.text = dateFormat.format(tarea.date)

            binding.btnEditar.setOnClickListener { onEdit(tarea) }
            binding.btnEliminar.setOnClickListener { onDelete(tarea) }
            binding.root.setOnClickListener { onClick(tarea) }

        }
    }
    fun addTareas(tareaItems: List<Tarea>) {
        tareas.clear()
        tareas.addAll(tareaItems)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentItemTareasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = tareas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(tareas[position])
    }

}