package com.example.myapplication.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R

data class DrinkCategory(val name: String, val imageRes: Int)

@Composable
fun OldProductPage(navController: NavController) {
    val drinkCategories = listOf(
        DrinkCategory("Café helado", R.drawable.cafe_helado),
        DrinkCategory("Capucchino", R.drawable.cappuccino1),
        DrinkCategory("Frappuccino", R.drawable.frapuccino),
        DrinkCategory("Americano", R.drawable.americano),
        DrinkCategory("Té Caliente", R.drawable.te),
        DrinkCategory("Té Helado", R.drawable.te_helado)
    )

    Scaffold(
        topBar = { TopBar(navController) },
        bottomBar = { BottomNavBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
        ) {
            DrinksMenu(categories = drinkCategories, navController = navController)
        }
    }
}

@Composable
fun DrinksMenu(categories: List<DrinkCategory>, navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Bebidas",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold, color = Color.Black)
        )
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { category ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { 
                        if (category.name == "Café helado") {
                            navController.navigate("detail")
                        }
                     }
                ) {
                    Image(
                        painter = painterResource(id = category.imageRes),
                        contentDescription = category.name,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF00332C)), // Dark green similar to image
                        contentScale = ContentScale.Inside
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = category.name,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OldProductPagePreview() {
    OldProductPage(rememberNavController())
}
