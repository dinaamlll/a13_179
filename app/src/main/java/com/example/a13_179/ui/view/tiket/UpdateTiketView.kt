package com.example.a13_179.ui.view.tiket

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.a13_179.ui.customwidget.BottomAppBarDefaults
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.view.peserta.EntryBodyPeserta
import com.example.a13_179.ui.viewmodel.tiket.PenyediaViewModelTiket
import com.example.a13_179.ui.viewmodel.tiket.UpdateTiketViewModel
import com.example.a13_179.ui.viewmodel.tiket.toTkt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateTiket : DestinasiNavigasi {
    override val route = "update_tiket"
    const val id_tiket = "id_tiket"
    val routesWithArg = "$route/{$id_tiket}"
    override val titleRes = "Update Tiket"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTiketView(
    onBack: () -> Unit,
    onNavigate:()-> Unit,
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateTiketViewModel = viewModel(factory = PenyediaViewModelTiket.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Collect the UI state from the ViewModel



    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onBackClick = onBack,
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
    ){padding ->
        EntryBodyTiket(
            modifier = Modifier.padding(padding),
            insertTiketUiState = viewModel.updateTiketUiState,
            onTiketValueChange = viewModel::updateInsertTiketState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateTiket()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}