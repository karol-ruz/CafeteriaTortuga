package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.screens.DetailPage
import com.example.myapplication.ui.screens.LoginPage
import com.example.myapplication.ui.screens.OldProductPage
import com.example.myapplication.ui.screens.ProductPage
import com.example.myapplication.ui.screens.RegisterPage
import com.example.myapplication.ui.screens.StartPage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginPage(navController) }
        composable("register") { RegisterPage(navController) }
        composable("start") { StartPage(navController) }
        composable("products") { ProductPage(navController) }
        composable("oldproducts") { OldProductPage(navController) }
        composable("detail/{productId}") { backStackEntry ->
            val productIdString = backStackEntry.arguments?.getString("productId")
            val productId = productIdString?.toLongOrNull()

            if (productId != null) {
                DetailPage(navController = navController, productId = productId)
            } else {
                // Handle the case where productId is null or invalid, e.g., navigate back or to an error screen
                navController.popBackStack() // Example: navigate back
            }
        }
    }
}
