package com.mtd.electrica.feature.signin.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtd.electrica.common.data.dataSource.local.LocalStorage
import com.mtd.electrica.feature.signin.domain.viewModel.UserViewModel
import javax.inject.Inject

@Composable
fun SigninScreen(
    viewModel: UserViewModel = hiltViewModel(),
    navController: NavController,
) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val uiState by viewModel.uiState.observeAsState()
    val type by viewModel.typeState.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.signIn(username.value, password.value) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        when (uiState) {
            is UserViewModel.UiState.Success -> {
                Text("Login Successful!")
                if (type.equals("admin")) {
                    navController.navigate("admin")
                } else
                    navController.navigate("user")
            }

            is UserViewModel.UiState.Error -> Text("Error: ${(uiState as UserViewModel.UiState.Error).message}")
            else -> {}
        }
    }
}
