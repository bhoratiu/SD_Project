package com.mtd.electrica.common

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.mtd.electrica.feature.user.data.model.EnergyConsumptionData
import com.mtd.electrica.feature.user.data.repository.Device
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EnergyConsumptionChart(data: List<EnergyConsumptionData>) {
    val entries = data.map {
        // Parse the timestamp string to LocalDateTime and then convert it to an Instant with a default zone
        val dateTime = LocalDateTime.parse(it.timestamp, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        // Assuming you want to display hours on the X-axis and energyConsumed on the Y-axis
        val hour = dateTime.hour.toFloat() + dateTime.minute.toFloat() / 60 // Converts time to a fraction
        Entry(hour, it.energyConsumed.toFloat())
    }
    val dataSet = LineDataSet(entries, "Energy Consumption")
    val lineData = LineData(dataSet)

    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                this.data = lineData
                invalidate()
            }
        },
        update = { chart ->
            chart.data = lineData
            chart.invalidate()
        },
        modifier = Modifier.fillMaxWidth()
            .height(200.dp)
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ShowDatePickerDialog(
    isDialogOpen: Boolean,
    onDateSelected: (LocalDate) -> Unit,
    onDialogDismiss: () -> Unit
) {
    val context = LocalContext.current

    if (isDialogOpen) {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            setOnDismissListener { onDialogDismiss() }
            show()
        }
    }
}