package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.data.model.ProductoDto
import com.example.myapplication.data.remote.RetrofitClient
import com.example.myapplication.data.repository.ProductoRepository
import com.example.myapplication.ui.ProductoViewModel
import com.example.myapplication.ui.ProductoViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductPage(navController: NavController) {
    val viewModel: ProductoViewModel = viewModel(factory = ProductoViewModelFactory(ProductoRepository(RetrofitClient.productoApi)))
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargar()
    }

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { BottomNavBar(navController) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = "Add Product")
            }
        }
    ) { paddingValues ->
        if (showDialog) {
            ProductoFormDialog(
                titulo = "Crear Producto",
                onDismiss = { showDialog = false },
                onGuardar = { nombre, descripcion, imagenUrl, precio, visibilidad ->
                    viewModel.crear(nombre, descripcion, imagenUrl, precio, visibilidad)
                    showDialog = false
                }
            )
        }
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
        ) {
            if (state.loading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (state.error != null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Error: ${state.error}",
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = { viewModel.cargar() }) {
                        Text("Reintentar")
                    }
                }
            } else {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Bebidas",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = Color.Black)
                    )
                    Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray)
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(top = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.products) { product ->
                            ProductGridItem(product = product, navController = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductGridItem(product: ProductoDto, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            product.id?.let { id -> navController.navigate("detail/${id}") }
        }
    ) {
        AsyncImage(
            model = if (product.imagenUrl.isNullOrEmpty()) R.drawable.ic_launcher_background else product.imagenUrl,
            contentDescription = product.nombre,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color(0xFF00332C)),
            contentScale = ContentScale.Inside
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = product.nombre,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        Text(
            text = "$${product.precio}",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}
