package com.example.a13_179.ui.view.tiket

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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.a13_179.ui.customwidget.BottomAppBarDefaults
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.viewmodel.tiket.InsertTiketUiEvent
import com.example.a13_179.ui.viewmodel.tiket.InsertTiketUiState
import com.example.a13_179.ui.viewmodel.tiket.InsertTiketViewModel
import com.example.a13_179.ui.viewmodel.tiket.PenyediaViewModelTiket
import kotlinx.coroutines.launch

object DestinasiEntryTiket: DestinasiNavigasi {
    override val route = "item_entry_tiket"
    override val titleRes = "Entry Tiket"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTiketScreen(
    navigateBack:()->Unit,
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTiketViewModel = viewModel(factory = PenyediaViewModelTiket.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onBackClick = navigateBack
            )
        },
        bottomBar = {
            BottomAppBarDefaults(
                navController = rememberNavController(),
                onEventClick = onEventClick,
                onPesertaClick = onPesertaClick,
                onTiketClick = onTiketClick,
                onTransaksiClick = onTransaksiClick
            )
        }

    ) { innerPadding ->
        EntryBodyTiket( //EntryBody untuk form input data peserta dengan tombol simpan.
            insertTiketUiState = viewModel.uiState,
            onTiketValueChange = viewModel::updateInsertTktState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTiket()
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
fun EntryBodyTiket(
    insertTiketUiState: InsertTiketUiState,
    onTiketValueChange: (InsertTiketUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputTiket(
            insertTiketUiEvent = insertTiketUiState.insertTiketUiEvent,
            onValueChange = onTiketValueChange,
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
fun FormInputTiket(
    insertTiketUiEvent: InsertTiketUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertTiketUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertTiketUiEvent.id_tiket.toString(),  // Convert Int to String for display
            onValueChange = { newText ->
                // Convert the new text back to Int (if possible) and update the state
                val newIdTiket = newText.toIntOrNull() ?: 0  // Default to 0 if conversion fails
                onValueChange(insertTiketUiEvent.copy(id_tiket = newIdTiket))
            },
            label = { Text("Id Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

    }
        OutlinedTextField(
            value = insertTiketUiEvent.id_event.toString(),  // Convert Int to String for display
            onValueChange = { newText ->
                // Convert the new text back to Int (if possible) and update the state
                val newIdEvent = newText.toIntOrNull() ?: 0  // Default to 0 if conversion fails
                onValueChange(insertTiketUiEvent.copy(id_event = newIdEvent))
            },
            label = { Text("Id Event") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

    OutlinedTextField(
        value = insertTiketUiEvent.id_peserta.toString(),  // Convert Int to String for display
        onValueChange = { newText ->
            // Convert the new text back to Int (if possible) and update the state
            val newIdPeserta = newText.toIntOrNull() ?: 0  // Default to 0 if conversion fails
            onValueChange(insertTiketUiEvent.copy(id_peserta = newIdPeserta))
        },
        label = { Text("Id Event") },
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        singleLine = true
    )


OutlinedTextField(
value = insertTiketUiEvent.kapasitas_tiket.toString(),  // Convert Int to String for display
onValueChange = { newText ->
    // Convert the new text back to Int (if possible) and update the state
    val newKapasitas = newText.toIntOrNull() ?: 0  // Default to 0 if conversion fails
    onValueChange(insertTiketUiEvent.copy(kapasitas_tiket = newKapasitas))
},
label = { Text("Id Event") },
modifier = Modifier.fillMaxWidth(),
enabled = enabled,
singleLine = true
)


OutlinedTextField(
value = insertTiketUiEvent.harga_tiket.toString(),  // Convert Int to String for display
onValueChange = { newText ->
    // Convert the new text back to Int (if possible) and update the state
    val newHarga = newText.toIntOrNull() ?: 0  // Default to 0 if conversion fails
    onValueChange(insertTiketUiEvent.copy(harga_tiket = newHarga))
},
label = { Text("Harga Tiket") },
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

