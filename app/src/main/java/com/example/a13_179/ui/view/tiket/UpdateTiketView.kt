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
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModel
import com.example.a13_179.ui.viewmodel.peserta.UpdatePesertaViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateTiket : DestinasiNavigasi {
    override val route = "update_tiket"
    const val ID_TIKET = "id_tiket"
    val routesWithArg = "$route/{$ID_TIKET}"
    override val titleRes = "Update Tiket"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateTiketView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateTiketViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Collect the UI state from the ViewModel
    val TiketuiState = viewModel.TiketuiState.value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateTiket.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // Pass the UI state to the EntryBody
            EntryBodyTiket(
                insertTiketUiState = TiketuiState,
                onTiketValueChange = { updatedValue ->
                    viewModel.updatePesertaState(updatedValue) // Update ViewModel state
                },
                onSaveClick = {
                    TiketuiState.insertPesertaUiEvent?.let { insertTiketUiEvent ->
                        coroutineScope.launch {
                            // Call ViewModel update method
                            viewModel.updatePsrta(
                                id_tiket = viewModel.id_tiket, // Pass the NIM from ViewModel
                                tiket = insertTiketUiEvent.toTkt() // Convert InsertUiEvent to Mahasiswa
                            )
                            navigateBack() // Navigate back after saving
                        }
                    }
                }
            )
        }
    }
}