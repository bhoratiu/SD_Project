import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.mtd.electrica.feature.admin.domain.viewModel.AdminViewModel
import com.mtd.electrica.feature.user.data.repository.Device

@Composable
fun AdminScreen(
    adminViewModel: AdminViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    val allDevices by adminViewModel.devices.collectAsState(emptyList())
    val errorMessage by adminViewModel.errorMessage.collectAsState()
    val succesMessage by adminViewModel.successMessage.collectAsState()

    var deviceId by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var maximumHourlyEnergyConsumption by remember { mutableStateOf("") }
    var usersIdForADevice by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                adminViewModel.loadDevices()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(key1 = errorMessage, block = {
        if (errorMessage.isNotBlank())
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    })

    LaunchedEffect(key1 = succesMessage, block = {
        if (succesMessage.isNotBlank())
            Toast.makeText(context, succesMessage, Toast.LENGTH_SHORT).show()
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(state = scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Admin Panel", style = MaterialTheme.typography.headlineMedium)
            Button(
                onClick = {
                    navController.navigate("login")
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(16.dp)
            ) {
                Text("Logout")
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = deviceId,
            onValueChange = { deviceId = it },
            label = { Text("Device ID") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = maximumHourlyEnergyConsumption,
            onValueChange = {
                maximumHourlyEnergyConsumption = it
            },
            label = { Text("Max Hourly Energy Consumption") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = usersIdForADevice,
            onValueChange = { usersIdForADevice = it },
            label = { Text("User Ids") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                modifier = Modifier.wrapContentWidth(),
                onClick = {
                    adminViewModel.addDevice(
                        deviceId.toLong(),
                        description,
                        maximumHourlyEnergyConsumption,
                        usersIdForADevice
                    )
                }) {
                Text("Add Device")
            }

            Button(
                modifier = Modifier.wrapContentWidth(),
                onClick = {
                    adminViewModel.updateDevice(
                        deviceId.toLongOrNull() ?: 0,
                        description,
                        maximumHourlyEnergyConsumption,
                        usersIdForADevice
                    )
                }) {
                Text("Update Device")
            }

            Button(modifier = Modifier.wrapContentWidth(),
                onClick = {
                    adminViewModel.deleteDevice(deviceId.toLongOrNull() ?: 0)
                }) {
                Text("Delete Device")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate("admin_manage_users")
        }) {
            Text("Go to user management screen")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("All Devices", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        allDevices.forEach { device ->
            DeviceItem(device = device)
        }
    }
}

@Composable
fun DeviceItem(device: Device) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("ID: ${device.id}", style = MaterialTheme.typography.bodyMedium)
            Text("Description: ${device.description}", style = MaterialTheme.typography.bodyMedium)
            Text(
                "Max Hourly Consumption: ${device.maximumHourlyEnergyConsumption}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                "Linked users: ${if(device.userIds?.isEmpty() == true) "Does not have any" else device.userIds} ",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}