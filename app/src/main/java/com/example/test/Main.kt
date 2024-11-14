package com.example.test

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class Main : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MyApp() {
        val navController = rememberNavController()
        Scaffold {
            NavHost(navController = navController, startDestination = "LoginPage") {
                composable("LoginPage") { LoginPage(navController) }
                composable("CreateAccountPage") { CreateAccountPage(navController) }
                composable("BankAccountPage") { BankAccountPage(navController) }
            }
        }
    }

    @Composable
    fun LoginPage(navController: NavController) {
        var text by remember { mutableStateOf("I love Denis!") }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text)

            Button(
                onClick = {
                    navController.navigate("BankAccountPage")
                }
            ) {
                Text(text = "Login")
                Color(0, 0, 0)
            }
        }
    }

    @Composable
    fun CreateAccountPage(navController: NavController) {
        Column {

        }
    }

    @Composable
    fun BankAccountPage(navController: NavController) {
        Column {

        }
    }

}