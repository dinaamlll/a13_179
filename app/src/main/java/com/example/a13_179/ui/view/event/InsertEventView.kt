package com.example.a13_179.ui.view.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.a13_179.ui.customwidget.BottomAppBarDefaults
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.viewmodel.event.InsertEventUiEvent
import com.example.a13_179.ui.viewmodel.event.InsertEventUiState
import com.example.a13_179.ui.viewmodel.event.InsertEventViewModel
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModelEvent
import kotlinx.coroutines.launch



object DestinasiEntryEvent : DestinasiNavigasi {
    override val route = "entry_event_event"
    override val titleRes = "Entry Event"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryEventScreen(
    navigateBack: () -> Unit,
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertEventViewModel = viewModel(factory = PenyediaViewModelEvent.Factory)
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
                scrollBehavior = scrollBehavior, //supaya tampilan app bar bisa berubah pas di-scroll, misalnya jadi kecil atau tetap di tempat.
                onBackClick = navigateBack //buat balik ke halaman sebelumnya.
            )
        }
    ){ innerPadding ->
        EntryBodyEvent(
            insertEventUiState = viewModel.uiState,
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
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyEvent(
    insertEventUiState: InsertEventUiState,
    onEventValueChange: (InsertEventUiEvent) -> Unit,
    onSaveClick: () -> Unit,
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

) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Menangani input id_event dengan benar
        OutlinedTextField(
            value = insertEventUiEvent.id_event.toString(),
            onValueChange = {
                // Safely parse the input as Int
                val idEvent = it.toIntOrNull() ?: 0  // Default to 0 if invalid
                onValueChange(insertEventUiEvent.copy(id_event = idEvent))
            },
            label = { Text("Id Event") },
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

        OutlinedTextField(
            value = insertEventUiEvent.tanggal_event,
            onValueChange = { onValueChange(insertEventUiEvent.copy(tanggal_event = it)) },
            label = { Text("Tanggal Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )


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
