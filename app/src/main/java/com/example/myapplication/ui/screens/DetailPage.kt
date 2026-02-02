package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myapplication.data.remote.RetrofitClient
import com.example.myapplication.data.repository.ProductoRepository
import com.example.myapplication.ui.ProductoViewModel
import com.example.myapplication.ui.ProductoViewModelFactory
import com.example.myapplication.ui.theme.Blue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPage(navController: NavController, productId: Long) {
    val viewModel: ProductoViewModel = viewModel(factory = ProductoViewModelFactory(ProductoRepository(RetrofitClient.productoApi)))
    val state by viewModel.state.collectAsState()
    val product = state.products.find { it.id == productId }

    LaunchedEffect(productId) {
        viewModel.obtenerProducto(productId)
    }

    val quantity = remember { mutableStateOf(1) }

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { paddingValues ->
        if (product != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)) {
                        AsyncImage(
                            model = product.imagenUrl,
                            contentDescription = "Product Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .padding(16.dp)
                                .background(Color.White.copy(alpha = 0.7f), CircleShape)
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }

                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = product.nombre,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "$${product.precio}",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        product.descripcion?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                textAlign = TextAlign.Justify
                            )
                        }
                    }
                }

                Divider()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(
                            onClick = { if (quantity.value > 1) quantity.value-- },
                            modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        ) {
                            Icon(Icons.Default.Remove, contentDescription = "Remove")
                        }
                        Text(
                            text = quantity.value.toString(),
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = 24.dp)
                        )
                        IconButton(
                            onClick = { quantity.value++ },
                            modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add")
                        }
                    }
                    Button(
                        onClick = { /* TODO: Add to cart */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Blue
                        )
                    ) {
                        Text("Agregar")
                    }
                }
            }
        } else {
            // Show a loading indicator or an error message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (state.loading) {
                    CircularProgressIndicator()
                } else if (state.error != null) {
                    Text("Error: ${state.error}")
                } else {
                    Text("Producto no encontrado")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPagePreview() {
    DetailPage(rememberNavController(), productId = 1L)
}
