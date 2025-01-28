package com.example.a13_179.ui.view.event

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
import com.example.a13_179.ui.viewmodel.event.InsertEventUiEvent
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModelEvent
import com.example.a13_179.ui.viewmodel.event.UpdateEventViewModel
import com.example.a13_179.ui.viewmodel.event.toEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateEvent : DestinasiNavigasi {
    override val route = "update_event"
    const val id_event = "id_event"
    val routeWithArgs = "$route/{$id_event}"
    override val titleRes = "Update Event"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateEventScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onNavigate:()-> Unit,
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    viewModel: UpdateEventViewModel = viewModel(factory = PenyediaViewModelEvent.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()


    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateEvent.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onBackClick = onBackClick,
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
        EntryBodyEvent(
            modifier = Modifier.padding(padding),
            insertEventUiState = viewModel.updateEventUiState,
            onEventValueChange = viewModel::updateInsertEventState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateEvent()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}