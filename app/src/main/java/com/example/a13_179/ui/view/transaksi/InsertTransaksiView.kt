package com.example.a13_179.ui.view.transaksi

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.viewmodel.tiket.PenyediaViewModelTransaksi
import com.example.a13_179.ui.viewmodel.transaksi.InsertTransaksiUiEvent
import com.example.a13_179.ui.viewmodel.transaksi.InsertTransaksiUiState
import com.example.a13_179.ui.viewmodel.transaksi.InsertTransaksiViewModel
import kotlinx.coroutines.launch

object DestinasiEntryTransaksi: DestinasiNavigasi {
    override val route = "item_entry_transaksi"
    override val titleRes = "Entry Transaksi"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTransaksiScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTransaksiViewModel = viewModel(factory = PenyediaViewModelTransaksi.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryTransaksi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyTransaksi( //EntryBody untuk form input data peserta dengan tombol simpan.
            insertTransaksiUiState = viewModel.TransaksiuiState,
            onTransaksiValueChange = viewModel::updateInsertTrnsksiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTrnsksi()
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
fun EntryBodyTransaksi(
    insertTransaksiUiState: InsertTransaksiUiState,
    onTransaksiValueChange: (InsertTransaksiUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInputTransaksi(
            insertTransaksiUiEvent = insertTransaksiUiState.insertTransaksiUiEvent,
            onValueChange = onTransaksiValueChange,
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
fun FormInputTransaksi(
    insertTransaksiUiEvent: InsertTransaksiUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertTransaksiUiEvent) -> Unit = {},
    enabled: Boolean = true
) {
    var showDatePicker by remember { mutableStateOf(false) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

    }
        OutlinedTextField(
            value = insertTransaksiUiEvent.id_transaksi?.toString() ?: "",
            onValueChange = { onValueChange(insertTransaksiUiEvent.copy(id_transaksi = it.toIntOrNull())) },
            label = { Text("Id Transaksi") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
    OutlinedTextField(
        value = insertTransaksiUiEvent.tanggal_transaksi,
        onValueChange = {
            onValueChange(insertTransaksiUiEvent.copy(tanggal_transaksi = it))
        },
        label = { Text("Nama") },
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        singleLine = true
    )
        OutlinedTextField(
            value = insertTransaksiUiEvent.id_tiket?.toString() ?: "",
            onValueChange = { onValueChange(insertTransaksiUiEvent.copy(id_tiket = it.toIntOrNull())) },
            label = { Text("Id Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertTransaksiUiEvent.jumlah_tiket?.toString() ?: "",
            onValueChange = { onValueChange(insertTransaksiUiEvent.copy(jumlah_tiket = it.toIntOrNull())) },
            label = { Text("Jumlah Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertTransaksiUiEvent.jumlah_pembayaran?.toString() ?: "",
            onValueChange = { onValueChange(insertTransaksiUiEvent.copy(jumlah_pembayaran = it.toIntOrNull())) },
            label = { Text("Jumlah Pembayaran") },
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


