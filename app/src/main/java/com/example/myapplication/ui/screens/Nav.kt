package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun TopBar(navController: NavController, title: String? = null, actions: @Composable RowScope.() -> Unit = {}) {
    var menuExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.weight(1f)) {
            IconButton(onClick = { menuExpanded = true }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.Black)
            }
            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(text = { Text("Ver Menú") }, onClick = { navController.navigate("products"); menuExpanded = false })
                DropdownMenuItem(text = { Text("Carrito") }, onClick = { navController.navigate("cart"); menuExpanded = false })
                DropdownMenuItem(text = { Text("Cuenta") }, onClick = { navController.navigate("account"); menuExpanded = false })
                DropdownMenuItem(text = { Text("Cerrar Sesión") }, onClick = {
                    navController.navigate("login") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                    menuExpanded = false
                })
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(2f)) {
            if (title != null) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black)
            } else {
                Text(text = "Endulza tu vida", fontSize = 18.sp, color = Color.Black)
                Text(text = "Cafetería La Tortuga", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color.Black)
            }
        }
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            actions()
        }
    }
}

@Composable
fun BottomNavBar(navController: NavController) {
    BottomAppBar(containerColor = Color.White) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            IconButton(onClick = { navController.navigate("start") }) {
                Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.Black)
            }
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.Bookmark, contentDescription = "Bookmark", tint = Color.Black)
            }
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.Notifications, contentDescription = "Notifications", tint = Color.Black)
            }
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.Black)
            }
        }
    }
}