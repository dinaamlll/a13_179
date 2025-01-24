package com.example.a13_179.ui.view.peserta

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModel
import com.example.a13_179.ui.viewmodel.peserta.HomePesertaViewModel


object DestinasiHomePeserta: DestinasiNavigasi {
    override val route ="home_peserta"
    override val titleRes = "Home Peserta"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePesertaScreen(
    navigateToPesertaEntry:()->Unit,
    modifier: Modifier=Modifier,
    onDetailClick: (String) -> Unit ={},
    viewModel: HomePesertaViewModel = viewModel(factory = PenyediaViewModel.Factory)

){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePeserta.titleRes,
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPsrta()
                }
            )
        },
        floatingActionButton = { //`FloatingActionButton` untuk navigasi ke entri peserta.
            FloatingActionButton(
                onClick = navigateToPesertaEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Peserta")
            }
        },
    ) { innerPadding->
        HomeStatus(
            homeUiState = viewModel.PesertaUIState,
            retryAction = {viewModel.getPsrta()}, modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,onDeleteClick = {
                viewModel.deletePsrta(it.nim)
                viewModel.getPsrta()
            }
        )
    }
}

