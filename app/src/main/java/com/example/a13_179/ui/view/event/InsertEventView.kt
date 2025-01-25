package com.example.a13_179.ui.view.event

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.viewmodel.event.InsertEventUiEvent
import com.example.a13_179.ui.viewmodel.event.InsertEventUiState
import com.example.a13_179.ui.viewmodel.event.InsertEventViewModel
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModel
import kotlinx.coroutines.launch
import java.util.Calendar


object DestinasiEntryEvent : DestinasiNavigasi {
    override val route = "item_entry_event"
    override val titleRes = "Entry Event"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryEventScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertEventViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val selectedDate = remember { mutableStateOf("") }
    val datePickerState = rememberDatePickerState()
    var isDatePickerVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryEvent.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyEvent(
            insertEventUiState = viewModel.EventuiState,
            onEventValueChange = viewModel::updateInsertEventState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertEvent()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            onDateSelected = { selectedDateValue ->
                selectedDate.value = selectedDateValue
                // Update tanggal di viewModel
                viewModel.updateInsertEventState(InsertEventUiEvent(tanggal_event = selectedDateValue))
            }
        )
    }
}

@Composable
fun EntryBodyEvent(
    insertEventUiState: InsertEventUiState,
    onEventValueChange: (InsertEventUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    onDateSelected: (String) -> Unit, // Accept the onDateSelected parameter
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        // Pass the UI state to FormInput
        FormInput(
            insertEventUiEvent = insertEventUiState.insertEventUiEvent,
            onValueChange = onEventValueChange,
            modifier = Modifier.fillMaxWidth(),
            onDateSelected = onDateSelected // Pass onDateSelected to FormInput
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}


@Composable
fun FormInput(
    insertEventUiEvent: InsertEventUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertEventUiEvent) -> Unit = {},
    enabled: Boolean = true,
    onDateSelected: (String) -> Unit // Menerima parameter onDateSelected
) {
    val context = LocalContext.current // Mendapatkan context dengan benar di dalam komposabel
    val showDatePicker = remember { mutableStateOf(false) } // Menyimpan status untuk menunjukkan dialog

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Menangani input id_event dengan benar
        OutlinedTextField(
            value = insertEventUiEvent.id_event?.toString() ?: "", // Menampilkan string kosong jika id_event null
            onValueChange = {
                val newId = it.toIntOrNull() // Mengonversi input menjadi Integer
                if (newId != null) {
                    onValueChange(insertEventUiEvent.copy(id_event = newId))
                }
            },
            label = { Text("ID") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertEventUiEvent.nama_event,
            onValueChange = {
                onValueChange(insertEventUiEvent.copy(nama_event = it))
            },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertEventUiEvent.deskripsi_event,
            onValueChange = {
                onValueChange(insertEventUiEvent.copy(deskripsi_event = it))
            },
            label = { Text("Deskripsi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        // Tanggal event, memunculkan DatePicker saat diklik
        OutlinedTextField(
            value = insertEventUiEvent.tanggal_event,
            onValueChange = {
                onValueChange(insertEventUiEvent.copy(tanggal_event = it))
            },
            label = { Text("Tanggal Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = {
                    // Menampilkan DatePicker
                    showDatePicker.value = true
                }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Pilih Tanggal")
                }
            }
        )

        // Memanggil DatePicker menggunakan Compose
        if (showDatePicker.value) {
            ShowDatePickerDialog(
                onDateSelected = { date ->
                    onDateSelected(date)
                    showDatePicker.value = false
                }
            )
        }

        // Menambahkan input untuk lokasi event
        OutlinedTextField(
            value = insertEventUiEvent.lokasi_event,
            onValueChange = {
                onValueChange(insertEventUiEvent.copy(lokasi_event = it))
            },
            label = { Text("Lokasi Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
}

@Composable
fun ShowDatePickerDialog(onDateSelected: (String) -> Unit) { //ShowDatePickerDialog untuk menampilkan dialog pemilih tanggal
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

    // Create a DatePickerDialog in Compose style
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                val date = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                onDateSelected(date)
            },
            year, month, dayOfMonth
        )
    }

    // Show the DatePicker dialog when triggered
    LaunchedEffect(Unit) {
        datePickerDialog.show()
    }
}
