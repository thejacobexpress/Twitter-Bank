package com.example.test

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.test.ui.theme.twitterBlue

data class account(var username: String, var password: String, var balance: Double)

var accounts : MutableState<List<account>> = mutableStateOf(List<account>(size = 1){account("Jacob", "pass", 100.0)})

val currentAccountIndex: MutableState<Int> = mutableStateOf(0)


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

            Image(
                painter = painterResource(id = R.mipmap.twitterlogo_foreground),
                contentDescription = null
            )

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

                    for(e in accounts.value.indices) {
                        print(e)
                        if(accounts.value.toMutableList().get(e).username == loginText && accounts.value.toMutableList()[e].password == passwordText) {
                            currentAccountIndex.value = e
                            navController.navigate("BankAccountPage")
                        } else if(accounts.value.get(e) == accounts.value.get(accounts.value.size-1)) {
                            Toast.makeText(context, "Username or password is incorrect!", Toast.LENGTH_LONG).show()
                        }
                    }

                },
                colors = ButtonDefaults.buttonColors(containerColor = twitterBlue)

            ) {
                Text(text = "Login")
            }

            Text(
                modifier = Modifier
                    .padding(top = 50.dp),
                text = "Don't have an account?"
            )

            Button(
                onClick = {
                    navController.navigate("CreateAccountPage")
                },
                colors = ButtonDefaults.buttonColors(containerColor = twitterBlue)
            ) {
                Text(text = "Create an Account")
            }

        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun CreateAccountPage(navController: NavController) {

        val context = LocalContext.current

        var usernameText by remember { mutableStateOf("") }
        var passwordText by remember { mutableStateOf("") }
        var reEnterPasswordText by remember { mutableStateOf("") }

//        var accs by remember { mutableStateOf(accounts) }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.mipmap.twitterlogo_foreground),
                contentDescription = null
            )

            OutlinedTextField(
                modifier = Modifier
                    .height(55.dp),

                value = usernameText,
                onValueChange = { it ->
                    usernameText = it
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

            OutlinedTextField(
                modifier = Modifier
                    .height(55.dp),

                value = reEnterPasswordText,
                onValueChange = { it ->
                    reEnterPasswordText = it
                },
                label = {
                    Text(text = "Re-enter password")
                }
            )

            Button(
                onClick = {

                    for(e in accounts.value.indices) {
                        if(usernameText == accounts.value.toMutableList().get(e).username && passwordText == accounts.value.toMutableList().get(e).password) {
                            Toast.makeText(context, "Username and password already exist!", Toast.LENGTH_LONG).show()
                            break
                        } else if(usernameText == accounts.value.toMutableList().get(e).username) {
                            Toast.makeText(context, "Username already exists!", Toast.LENGTH_LONG).show()
                            break
                        } else {
                            if(passwordText == reEnterPasswordText) {
                                val accs = accounts.value.toMutableList().apply { add(account(usernameText, passwordText, 0.0)) }
                                accounts.value = accs
                                navController.navigate("LoginPage")
                                Toast.makeText(context, "Successfully created an account!", Toast.LENGTH_LONG).show()
                            } else {
                                Toast.makeText(context, "Passwords must match!", Toast.LENGTH_LONG).show()
                                break
                            }
                        }
                    }

                },
                colors = ButtonDefaults.buttonColors(containerColor = twitterBlue)
            ) {
                Text(text = "Create Account")
            }

            Text(
                modifier = Modifier
                    .padding(top = 50.dp),
                text = "Already have an account?"
            )

            Button(
                onClick = {
                    navController.navigate("LoginPage")
                },
                colors = ButtonDefaults.buttonColors(containerColor = twitterBlue)
            ) {
                Text(text = "Login")
            }

        }
    }

    @Composable
    fun BankAccountPage(navController: NavController) {

        var balanceCurrent by remember { mutableStateOf(accounts.value.get(currentAccountIndex.value).balance) }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "$${balanceCurrent.toString()}",
                fontSize = 30.sp
            )

            Button(
                onClick = {
                    accounts.value.get(currentAccountIndex.value).balance = balanceCurrent
                    navController.navigate("DepositPage")
                },
                colors = ButtonDefaults.buttonColors(containerColor = twitterBlue)
            ) {
                Text(text = "Deposit")
            }

            Button(
                onClick = {
                    accounts.value.get(currentAccountIndex.value).balance = balanceCurrent
                    navController.navigate("WithdrawPage")
                },
                colors = ButtonDefaults.buttonColors(containerColor = twitterBlue)
            ) {
                Text(text = "Withdraw")
            }

            Button(
                modifier = Modifier
                    .padding(30.dp),
                onClick = {
                    balanceCurrent *= 2
//                    balanceStr = (balanceStr.toDouble() *= 2).toString()
                },
                colors = ButtonDefaults.buttonColors(containerColor = twitterBlue)
            ) {
                Text(text = "MULTIPLY MONEY")
            }

        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Button(
                onClick = {
                    navController.navigate("LoginPage")
                },
                colors = ButtonDefaults.buttonColors(containerColor = twitterBlue)
            ) {
                Text(text = "Logout")
            }

        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DepositPage(navController: NavController) {

        val context = LocalContext.current

        var depositInputText : String by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "How much would you like to deposit?"
            )

            TextField(
                modifier = Modifier
                    .height(55.dp),
                value = depositInputText,
                onValueChange = {it ->
                    depositInputText = it
                }
            )

            Button(
                onClick = {
                    try {
                        accounts.value.get(currentAccountIndex.value).balance += depositInputText.toDouble()
                        navController.navigate("BankAccountPage")
                    } catch (e : Exception) {
                        Toast.makeText(context, "Please enter a number.", Toast.LENGTH_LONG).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = twitterBlue)
            ) {
                Text(text = "Deposit")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = {
                    navController.navigate("BankAccountPage")
                },
                colors = ButtonDefaults.buttonColors(containerColor = twitterBlue)
            ) {
                Text(text = "Back")
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun WithdrawPage(navController: NavController) {

        val context = LocalContext.current

        var withdrawInputText : String by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "How much would you like to withdraw?"
            )

            TextField(
                modifier = Modifier
                    .height(55.dp),
                value = withdrawInputText,
                onValueChange = {it ->
                    withdrawInputText = it
                }
            )

            Button(
                onClick = {
                    try {
                        accounts.value.get(currentAccountIndex.value).balance -= withdrawInputText.toDouble()
                        navController.navigate("BankAccountPage")
                    } catch (e : Exception) {
                        Toast.makeText(context, "Please enter a number.", Toast.LENGTH_LONG).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = twitterBlue)
            ) {
                Text(text = "Withdraw")
            }

        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick = {
                    navController.navigate("BankAccountPage")
                },
                colors = ButtonDefaults.buttonColors(containerColor = twitterBlue)
            ) {
                Text(text = "Back")
            }
        }

    }

}