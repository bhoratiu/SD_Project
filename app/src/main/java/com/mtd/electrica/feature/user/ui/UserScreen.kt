package com.mtd.electrica.feature.user.ui

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mtd.electrica.common.DeviceItem
import com.mtd.electrica.feature.signin.domain.viewModel.UserViewModel
import com.mtd.electrica.feature.user.domain.viewModel.DeviceViewModel

@Composable
fun UserScreen(
    viewModel: DeviceViewModel = hiltViewModel(),
    navController: NavController
) {
    val devices by viewModel.devices.observeAsState(emptyList())
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.loadUserDevices()
    }

    Column {
        devices.forEach { device ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = device.description ?: "No description"
                    )
                    if (device.maximumHourlyEnergyConsumption != null) {
                        Text(text = "Max Consumption: ${device.maximumHourlyEnergyConsumption}")
                    } else {
                        Text(text = "Max Consumption: Not Available")
                    }
                }
            }
        }

        Button(
            onClick = {
                navController.navigate("login")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Logout")
        }
    }
}
