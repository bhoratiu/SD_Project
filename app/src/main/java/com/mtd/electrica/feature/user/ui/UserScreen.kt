package com.mtd.electrica.feature.user.ui

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.mtd.electrica.common.DeviceItem
import com.mtd.electrica.common.EnergyConsumptionChart
import com.mtd.electrica.common.ShowDatePickerDialog
import com.mtd.electrica.feature.signin.domain.viewModel.UserViewModel
import com.mtd.electrica.feature.user.domain.viewModel.DeviceViewModel
import com.mtd.electrica.utils.IP
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserScreen(
    viewModel: DeviceViewModel = hiltViewModel(),
    navController: NavController
) {
    val devices by viewModel.devices.observeAsState(emptyList())
    val context = LocalContext.current

    val (lastNotification, setLastNotification) = remember { mutableStateOf<String?>(null) }

    val client = remember { OkHttpClient() }

    val selectedDate = remember { mutableStateOf(LocalDate.of(2021, 1, 1)) }


    LaunchedEffect(key1 = Unit) {
        viewModel.loadUserDevices()
        val request = Request.Builder().url("ws://10.0.2.2:8087/ws").build()
        val wsListener = object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                setLastNotification(text)
            }

            // Implement other methods as necessary
        }
        viewModel.connectWebSocket()
        client.newWebSocket(request, wsListener)
    }

    // Observe WebSocket messages
    val webSocketMessage by viewModel.webSocketMessage.observeAsState()

    LaunchedEffect(key1 = webSocketMessage, block = {
        if (webSocketMessage?.isNotBlank() == true)
            Toast.makeText(context, webSocketMessage, Toast.LENGTH_SHORT).show()
    })

    val associationStatus by viewModel.associationStatus.observeAsState()

    val deviceIdInput = remember { mutableStateOf("") }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                viewModel.loadEnergyData(selectedDate.toString())
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        lastNotification?.let {
            Text("Last notification: $it")
        }

        Spacer(modifier = Modifier.height(50.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = deviceIdInput.value,
            onValueChange = { deviceIdInput.value = it },
            label = { Text("Device ID") }
        )

        Button(onClick = {
            viewModel.associateDevice(deviceIdInput.value.toLongOrNull() ?: -1)
            viewModel.loadUserDevices()
        }) {
            Text("Associate Device")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(associationStatus ?: "")
        val context = LocalContext.current
        val (isDatePickerOpen, setDatePickerOpen) = remember { mutableStateOf(false) }

        val historicalData by viewModel.energyData.observeAsState(emptyList())

        Button(
            onClick = { setDatePickerOpen(true) },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Select Date")
        }

        Text("Selected Date: ${selectedDate.value}")

        ShowDatePickerDialog(
            isDialogOpen = isDatePickerOpen,
            onDateSelected = { date ->
                selectedDate.value = date
                viewModel.loadEnergyData(date.toString())
            },
            onDialogDismiss = { setDatePickerOpen(false) }
        )

        EnergyConsumptionChart(historicalData)

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

    DisposableEffect(key1 = Unit) {
        onDispose {
            client.dispatcher.executorService.shutdown()
        }
    }
}