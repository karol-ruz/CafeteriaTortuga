package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.navigation.NavController
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.model.ProductoDto
import com.example.myapplication.ui.ProductoViewModel

@Composable
fun ProductoListScreen(vm: ProductoViewModel, navController: NavController) {
    val state by vm.state.collectAsState()

    var mostrarCrear by remember { mutableStateOf(false) }
    var editarProducto by remember { mutableStateOf<ProductoDto?>(null) }

    LaunchedEffect(Unit) {
        vm.cargar()
    }

    Scaffold(
        topBar = {
            TopBar(navController, title = "Productos (Retrofit CRUD)")
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { mostrarCrear = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Product")
            }
        }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            state.error?.let {
                Text("Error: $it")
                Spacer(Modifier.height(8.dp))
            }

            if (state.loading) {
                CircularProgressIndicator()
                Spacer(Modifier.height(12.dp))
            }

            Button(onClick = { vm.cargar() }) {
                Text("Recargar")
            }
            Spacer(Modifier.height(12.dp))

            LazyColumn {
                items(state.products) { p ->
                    ProductoItem(
                        producto = p,
                        onEditar = { editarProducto = it },
                        onEliminar = { vm.eliminar(p.id ?: 0) }
                    )
                }
            }
        }
    }

    if (mostrarCrear) {
        ProductoFormDialog(
            titulo = "Crear Producto",
            onDismiss = { mostrarCrear = false },
            onGuardar = { nombre, descripcion, imagenUrl, precio, visibilidad ->
                vm.crear(nombre, descripcion, imagenUrl, precio, visibilidad)
                mostrarCrear = false
            }
        )
    }

    editarProducto?.let { producto ->
        ProductoFormDialog(
            titulo = "Editar Producto",
            inicial = producto,
            onDismiss = { editarProducto = null },
            onGuardar = { nombre, descripcion, imagenUrl, precio, visibilidad ->
                vm.actualizar(producto.id ?: 0, nombre, descripcion, imagenUrl, precio, visibilidad)
                editarProducto = null
            }
        )
    }
}

@Composable
fun ProductoItem(
    producto: ProductoDto,
    onEditar: (ProductoDto) -> Unit,
    onEliminar: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Nombre: ${producto.nombre}", style = MaterialTheme.typography.titleMedium)
            producto.descripcion?.let {
                Text(text = "Descripción: $it", style = MaterialTheme.typography.bodyMedium)
            }
            producto.imagenUrl?.let {
                Text(text = "Imagen URL: $it", style = MaterialTheme.typography.bodySmall)
            }
            Text(text = "Precio: $${producto.precio}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Visibilidad: ${if (producto.visibilidad) "Visible" else "No Visible"}", style = MaterialTheme.typography.bodySmall)
            Row {
                TextButton(onClick = { onEditar(producto) }) {
                    Text("Editar")
                }
                TextButton(onClick = onEliminar) {
                    Text("Eliminar")
                }
            }
        }
    }
}