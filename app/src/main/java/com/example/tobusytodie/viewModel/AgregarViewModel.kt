package com.example.tobusytodie.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tobusytodie.core.ResultWrapper
import com.example.tobusytodie.model.Tarea
import com.example.tobusytodie.network.TareaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AgregarViewModel @Inject constructor(
    private val tareaRepository: TareaRepository
) : ViewModel() {

    private val _tareas = MutableLiveData<List<Tarea>>()
    val tareas: LiveData<List<Tarea>> get() = _tareas

    private val _selectedTarea = MutableLiveData<Tarea>()
    val selectedTarea: LiveData<Tarea> get() = _selectedTarea

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun createTarea(tarea: Tarea) {
        viewModelScope.launch {
            _loading.value = true
            val result = tareaRepository.createTarea(tarea)
            handleResult(result)
            _loading.value = false
        }
    }

    fun getTareas() {
        viewModelScope.launch {
            _loading.value = true
            when (val result = tareaRepository.getTareas()) {
                is ResultWrapper.Success -> {
                    if (result.data is List<*>) {
                        @Suppress("UNCHECKED_CAST")
                        _tareas.value = result.data as List<Tarea>
                    } else {
                        _error.value = "Formato de datos incorrecto"
                    }
                }
                is ResultWrapper.Error -> _error.value = result.exception.message
            }
            _loading.value = false
        }
    }

    fun getTareaById(id: String) {
        viewModelScope.launch {
            _loading.value = true
            when (val result = tareaRepository.getTareas(id)) {
                is ResultWrapper.Success -> {
                    if (result.data is Tarea) {
                        _selectedTarea.value = result.data
                    } else {
                        _error.value = "No se pudo obtener la tarea"
                    }
                }
                is ResultWrapper.Error -> _error.value = result.exception.message
            }
            _loading.value = false
        }
    }

    fun updateTarea(tarea: Tarea) {
        viewModelScope.launch {
            _loading.value = true
            val result = tareaRepository.updateTarea(tarea)
            handleResult(result)
            _loading.value = false
        }
    }

    fun deleteTarea(id: String) {
        viewModelScope.launch {
            _loading.value = true
            val result = tareaRepository.deleteTarea(id)
            handleResult(result)
            _loading.value = false
        }
    }

    private fun handleResult(result: ResultWrapper<Void>) {
        when (result) {
            is ResultWrapper.Success -> _error.value = null
            is ResultWrapper.Error -> _error.value = result.exception.message
        }
    }

    fun clearError() {
        _error.value = null
    }
}
