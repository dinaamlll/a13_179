package com.example.a13_179.ui.view.peserta

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
import com.example.a13_179.ui.viewmodel.peserta.PenyediaViewModelPeserta
import com.example.a13_179.ui.viewmodel.peserta.UpdatePesertaViewModel
import com.example.a13_179.ui.viewmodel.peserta.toPsrta
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


object DestinasiUpdatePeserta : DestinasiNavigasi {
    override val route = "update_peserta"
    const val id_peserta = "id_peserta"
    val routeWithArgs = "$route/{$id_peserta}"
    override val titleRes = "Update Peserta"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePesertaScreen(
    onBack: () -> Unit,
    onNavigate:()-> Unit,
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePesertaViewModel = viewModel(factory = PenyediaViewModelPeserta.Factory)
){

    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePeserta.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onBackClick = onBack

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
        EntryBodyPeserta(
            modifier = Modifier.padding(padding),
            insertPesertaUiState = viewModel.updatePesertaUiState,
            onPesertaValueChange = viewModel::updateInsertPesertaState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePeserta()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}