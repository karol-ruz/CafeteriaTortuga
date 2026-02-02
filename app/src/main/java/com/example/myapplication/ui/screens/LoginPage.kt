package com.example.myapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.remote.RetrofitClient
import com.example.myapplication.data.repository.UserRepository
import com.example.myapplication.ui.UserViewModel
import com.example.myapplication.ui.UserViewModelFactory
import com.example.myapplication.ui.theme.Blue
import com.example.myapplication.ui.theme.Green

@Composable
fun LoginPage(navController: NavController) {
    val viewModel: UserViewModel = viewModel(factory = UserViewModelFactory(UserRepository(RetrofitClient.userApi)))
    val state by viewModel.state.collectAsState()

    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    LaunchedEffect(state.loginSuccess) {
        if (state.loginSuccess) {
            navController.navigate("start")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(Green, Blue)))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Inicio de sesión", style = TextStyle(color = Color.White, fontSize = 30.sp))
            Spacer(modifier = Modifier.height(60.dp))
            TextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text("Usuario") },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(0.75f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(0.75f)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { viewModel.login(username.value, password.value) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth(0.75f).height(50.dp)
            ) {
                Text(text = "Entrar")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "¿Aun no tienes cuenta? Registrate.",
                modifier = Modifier.clickable { navController.navigate("register") }
            )
            state.error?.let {
                Text(text = it, color = Color.Red)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPagePreview() {
    LoginPage(rememberNavController())
}
