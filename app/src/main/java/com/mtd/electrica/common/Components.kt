package com.mtd.electrica.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mtd.electrica.feature.user.data.repository.Device

@Composable
fun DeviceItem(device: Device) {
    Card(modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth()) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Device Name: ${device.description}")
            Text(text = "Device Consumption: ${device.maximumHourlyEnergyConsumption}")
        }
    }
}