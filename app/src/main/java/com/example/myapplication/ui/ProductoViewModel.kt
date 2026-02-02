package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.ProductoDto
import com.example.myapplication.data.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UiState(
    val loading: Boolean = false,
    val products: List<ProductoDto> = emptyList(),
    val error: String? = null
)

class ProductoViewModel(private val repo: ProductoRepository) : ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    fun cargar() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            runCatching { repo.listar() }
                .onSuccess {
                    _state.value = UiState(loading = false, products = it)
                }
                .onFailure { e ->
                    val errorMessage = when (e) {
                        is java.io.IOException -> "No internet connection"
                        is retrofit2.HttpException -> "Server error: ${e.code()}"
                        else -> "Unknown error: ${e.localizedMessage}"
                    }
                    _state.value = UiState(loading = false, error = errorMessage)
                }
        }
    }

    fun obtenerProducto(id: Long) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            runCatching { repo.obtener(id) }
                .onSuccess {
                    _state.value = UiState(loading = false, products = listOf(it))
                }
                .onFailure { e ->
                    val errorMessage = when (e) {
                        is java.io.IOException -> "No internet connection"
                        is retrofit2.HttpException -> "Server error: ${e.code()}"
                        else -> "Unknown error: ${e.localizedMessage}"
                    }
                    _state.value = UiState(loading = false, error = errorMessage)
                }
        }
    }

    fun crear(nombre: String, descripcion: String?, imagenUrl: String?, precio: Int, visibilidad: Boolean) {
        viewModelScope.launch {
            runCatching { repo.crear(nombre, descripcion, imagenUrl, precio, visibilidad) }
                .onSuccess { cargar() }
                .onFailure { e ->
                    val errorMessage = when (e) {
                        is java.io.IOException -> "No internet connection"
                        is retrofit2.HttpException -> "Server error: ${e.code()}"
                        else -> "Unknown error: ${e.localizedMessage}"
                    }
                    _state.value = _state.value.copy(error = errorMessage)
                }
        }
    }

    fun actualizar(id: Long, nombre: String, descripcion: String?, imagenUrl: String?, precio: Int, visibilidad: Boolean) {
        viewModelScope.launch {
            runCatching { repo.actualizar(id, nombre, descripcion, imagenUrl, precio, visibilidad) }
                .onSuccess { cargar() }
                .onFailure { e ->
                    val errorMessage = when (e) {
                        is java.io.IOException -> "No internet connection"
                        is retrofit2.HttpException -> "Server error: ${e.code()}"
                        else -> "Unknown error: ${e.localizedMessage}"
                    }
                    _state.value = _state.value.copy(error = errorMessage)
                }
        }
    }

    fun eliminar(id: Long) {
        viewModelScope.launch {
            runCatching { repo.eliminar(id) }
                .onSuccess { cargar() }
                .onFailure { e ->
                    val errorMessage = when (e) {
                        is java.io.IOException -> "No internet connection"
                        is retrofit2.HttpException -> "Server error: ${e.code()}"
                        else -> "Unknown error: ${e.localizedMessage}"
                    }
                    _state.value = _state.value.copy(error = errorMessage)
                }
        }
    }
}