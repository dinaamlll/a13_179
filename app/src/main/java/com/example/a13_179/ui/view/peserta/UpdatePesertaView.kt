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
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModel
import com.example.a13_179.ui.viewmodel.peserta.UpdatePesertaViewModel
import com.example.a13_179.ui.viewmodel.peserta.toPsrta
import kotlinx.coroutines.launch

object DestinasiUpdatePeserta : DestinasiNavigasi {
    override val route = "update_peserta"
    const val ID_PESERTA = "id_peserta"
    val routesWithArg = "$route/{$ID_PESERTA}"
    override val titleRes = "Update Peserta"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePesertaView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePesertaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Collect the UI state from the ViewModel
    val PesertauiState = viewModel.PesertauiState.value

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdatePeserta.titleRes,
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
            EntryBodyPeserta(
                insertPesertaUiState = PesertauiState,
                onPesertaValueChange = { updatedValue ->
                    viewModel.updatePesertaState(updatedValue) // Update ViewModel state
                },
                onSaveClick = {
                    PesertauiState.insertPesertaUiEvent?.let { insertPesertaUiEvent ->
                        coroutineScope.launch {
                            // Call ViewModel update method
                            viewModel.updatePsrta(
                                id_peserta = viewModel.id_peserta, // Pass the NIM from ViewModel
                                peserta = insertPesertaUiEvent.toPsrta() // Convert InsertUiEvent to Mahasiswa
                            )
                            navigateBack() // Navigate back after saving
                        }
                    }
                }
            )
        }
    }
}