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
import com.example.a13_179.ui.customwidget.CostumeTopAppBar
import com.example.a13_179.ui.navigation.DestinasiNavigasi
import com.example.a13_179.ui.viewmodel.event.InsertEventUiEvent
import com.example.a13_179.ui.viewmodel.event.PenyediaViewModel
import com.example.a13_179.ui.viewmodel.event.UpdateEventViewModel
import com.example.a13_179.ui.viewmodel.event.toEvent
import kotlinx.coroutines.launch

object DestinasiUpdateEvent : DestinasiNavigasi {
    override val route = "update_event"
    const val ID_EVENT = "id_event"
    val routesWithArg = "$route/{$ID_EVENT}"
    override val titleRes = "Update Event"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateEventView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateEventViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    // Collect the UI state from the ViewModel
    val EventuiState = viewModel.EventuiState.value

    // Define the onDateSelected function to handle date selection
    val onDateSelected: (String) -> Unit = { selectedDate ->
        // Update the ViewModel state with the selected date
        viewModel.updateEventState(InsertEventUiEvent(tanggal_event = selectedDate))
    }


    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateEvent.titleRes,
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
            // Pass the onDateSelected function to EntryBodyEvent
            EntryBodyEvent(
                insertEventUiState = EventuiState,
                onValueChange = { updatedValue ->
                    viewModel.updateEventState(updatedValue) // Update ViewModel state
                },
                onSaveClick = {
                    EventuiState.insertEventUiEvent?.let { insertEventUiEvent ->
                        coroutineScope.launch {
                            // Call ViewModel update method
                            viewModel.updateEvents(
                                id_event = viewModel.id_event, // Pass the NIM from ViewModel
                                event = insertEventUiEvent.toEvent() // Convert InsertUiEvent to Event
                            )
                            navigateBack() // Navigate back after saving
                        }
                    }
                },
                onDateSelected = onDateSelected // Pass the onDateSelected function
            )
        }
    }
}
