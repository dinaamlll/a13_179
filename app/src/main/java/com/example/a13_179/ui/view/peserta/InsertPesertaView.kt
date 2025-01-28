package com.example.a13_179.ui.view.peserta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.viewmodel.peserta.InsertPesertaUiEvent
import com.example.a13_179.ui.viewmodel.peserta.InsertPesertaUiState
import com.example.a13_179.ui.viewmodel.peserta.InsertPesertaViewModel
import com.example.a13_179.ui.viewmodel.peserta.PenyediaViewModelPeserta
import kotlinx.coroutines.launch

object DestinasiEntryPeserta: DestinasiNavigasi {
    override val route = "entry_peserta"
    override val titleRes = "Entry Peserta"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPesertaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    viewModel: InsertPesertaViewModel = viewModel(factory = PenyediaViewModelPeserta.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryPeserta.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onBackClick = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyPeserta( //EntryBody untuk form input data peserta dengan tombol simpan.
            insertPesertaUiState = viewModel.uiState,
            onPesertaValueChange = viewModel::updateInsertPesertaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPeserta()
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
fun EntryBodyPeserta(
    insertPesertaUiState: InsertPesertaUiState,
    onPesertaValueChange: (InsertPesertaUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputPeserta(
            insertPesertaUiEvent = insertPesertaUiState.insertPesertaUiEvent,
            onValueChange = onPesertaValueChange,
            modifier = Modifier.fillMaxWidth()
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputPeserta(
    insertPesertaUiEvent: InsertPesertaUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertPesertaUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertPesertaUiEvent.id_peserta.toString(),  // Convert Int to String for display
            onValueChange = { newText ->
                // Convert the new text back to Int (if possible) and update the state
                val newIdPeserta = newText.toIntOrNull() ?: 0  // Default to 0 if conversion fails
                onValueChange(insertPesertaUiEvent.copy(id_peserta = newIdPeserta))
            },
            label = { Text("Id Peserta") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    }
        OutlinedTextField(
            value = insertPesertaUiEvent.nama_peserta,
            onValueChange = { onValueChange(insertPesertaUiEvent.copy(nama_peserta = it)) },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )



        OutlinedTextField(
            value = insertPesertaUiEvent.email,
            onValueChange = { onValueChange(insertPesertaUiEvent.copy(email = it)) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertPesertaUiEvent.nomor_telepon,
            onValueChange = { onValueChange(insertPesertaUiEvent.copy(nomor_telepon = it)) },
            label = { Text("Nomor Telepon") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp)
            )
        }

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }