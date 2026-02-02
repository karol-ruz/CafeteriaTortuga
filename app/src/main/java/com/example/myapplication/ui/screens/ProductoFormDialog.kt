package com.example.myapplication.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import com.example.myapplication.data.model.ProductoDto

@Composable
fun ProductoFormDialog(
    titulo: String,
    inicial: ProductoDto? = null,
    onDismiss: () -> Unit,
    onGuardar: (nombre: String, descripcion: String?, imagenUrl: String?, precio: Int, visibilidad: Boolean) -> Unit
) {
    var nombre by remember { mutableStateOf(inicial?.nombre ?: "") }
    var descripcion by remember { mutableStateOf(inicial?.descripcion ?: "") }
    var imagenUrl by remember { mutableStateOf(inicial?.imagenUrl ?: "") }
    var precioTxt by remember { mutableStateOf(inicial?.precio?.toString() ?: "") }
    var visibilidad by remember { mutableStateOf(inicial?.visibilidad ?: true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(titulo) },
        text = {
            Column {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = imagenUrl,
                    onValueChange = { imagenUrl = it },
                    label = { Text("URL de la Imagen") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = precioTxt,
                    onValueChange = { precioTxt = it },
                    label = { Text("Precio") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = visibilidad,
                        onCheckedChange = { visibilidad = it }
                    )
                    Text("Visible")
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val precio = precioTxt.toIntOrNull() ?: 0
                    onGuardar(nombre.trim(), descripcion.trim().takeIf { it.isNotBlank() }, imagenUrl.trim().takeIf { it.isNotBlank() }, precio, visibilidad)
                }
            ) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}