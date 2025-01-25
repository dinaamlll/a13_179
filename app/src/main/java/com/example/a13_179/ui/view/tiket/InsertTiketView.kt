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
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModel
import com.example.a13_179.ui.viewmodel.tiket.InsertTiketUiEvent
import com.example.a13_179.ui.viewmodel.tiket.InsertTiketUiState
import com.example.a13_179.ui.viewmodel.tiket.InsertTiketViewModel
import kotlinx.coroutines.launch

object DestinasiEntryTiket: DestinasiNavigasi {
    override val route = "item_entry_tiket"
    override val titleRes = "Entry Tiket"

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTiketScreen(
    navigateBack:()->Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyTiket( //EntryBody untuk form input data tiket dengan tombol simpan.
            insertTiketUiState = viewModel.TiketuiState,
            onTiketValueChange = viewModel::updateInsertTktState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertTkt()
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
            value = insertTiketUiEvent.id_tiket?.toString() ?: "",
            onValueChange = {
                onValueChange(insertTiketUiEvent.copy(id_tiket = it.toIntOrNull()))
            },
            label = { Text("Id Tiket") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
       // OutlinedTextField(
        //    value = insertTiketUiEvent.nomor_telepon,
         //   onValueChange = { onValueChange(insertTiketUiEvent.copy(nomor_telepon = it)) },
         //   label = { Text("Kapasitas Tiket") },
          //  modifier = Modifier.fillMaxWidth(),
          //  enabled = enabled,
          //  singleLine = true
       // )
        OutlinedTextField(
            value = insertTiketUiEvent.harga_tiket,
            onValueChange = {onValueChange(insertTiketUiEvent.copy(harga_tiket = it))},
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
}
