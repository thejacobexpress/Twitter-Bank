package com.example.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

val loginKey : MutableState<String> = mutableStateOf("Jacob")
val passwordKey : MutableState<String> = mutableStateOf("pass")

val balance = mutableSetOf(1000.0)

val errorMessage = mutableStateOf(false)

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
                composable("DepositPage") { DepositPage(navController) }
                composable("WithdrawPage") { WithdrawPage(navController) }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginPage(navController: NavController) {

        val context = LocalContext.current

        var loginText by remember { mutableStateOf("") }
        var passwordText by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .height(55.dp),

                value = loginText,
                onValueChange = { it ->
                    loginText = it
                },
                label = {
                    Text(text = "Username")
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .height(55.dp),

                value = passwordText,
                onValueChange = { it ->
                    passwordText = it
                },
                label = {
                    Text(text = "Password")
                }
            )

            Button(
                onClick = {

                    if(loginKey.value == loginText && passwordKey.value == passwordText) {
                        navController.navigate("BankAccountPage")
                    } else if(loginKey.value == loginText && passwordKey.value != passwordText) {
                        Toast.makeText(context, "Password is incorrect!", Toast.LENGTH_LONG).show()
                    } else if(loginKey.value == loginText && passwordKey.value != passwordText) {
                        Toast.makeText(context, "Username is incorrect!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "SOMETHING IS WRONG!", Toast.LENGTH_LONG).show()
                    }

                }
            ) {
                Text(text = "Login")
            }

            Text(
                text = "Don't have an account?"
            )

            Button(
                onClick = {
                    navController.navigate("CreateAccountPage")
                }
            ) {
                Text(text = "Create an Account")
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
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = balance.toString()
            )

            Button(
                onClick = {

                }
            ) {
                Text(text = "Deposit")
            }

            Button(
                onClick = {

                }
            ) {
                Text(text = "Withdraw")
            }

        }
    }

    @Composable
    fun DepositPage(navController: NavController) {
        Column {

        }
    }

    @Composable
    fun WithdrawPage(navController: NavController) {
        Column {

        }
    }

}