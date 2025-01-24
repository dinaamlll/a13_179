package com.example.a13_179.ui.navigation

import com.example.a13_179.ui.view.event.DestinasiDetailEvent
import com.example.a13_179.ui.view.event.DestinasiEntryEvent
import com.example.a13_179.ui.view.event.DestinasiHomeEvent
import com.example.a13_179.ui.view.event.EntryEventScreen
import com.example.a13_179.ui.view.event.HomeEventScreen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a13_179.ui.view.event.DestinasiUpdateEvent
import com.example.a13_179.ui.view.event.DetailEventlView
import com.example.a13_179.ui.view.event.UpdateEventView


@Composable
fun PengelolaHalaman(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeEvent.route,
        modifier = Modifier,
    ){
        composable(DestinasiHomeEvent.route){
            HomeEventScreen(
                navigateToItemEntry = {navController.navigate(DestinasiEntryEvent.route)},
                onDetailClick = { id ->
                    // Navigasi ke destinasi Detail dengan menyertakan nim
                    navController.navigate("${DestinasiDetailEvent.route}/$id") {
                        // Menggunakan popUpTo untuk memastikan navigasi ke Detail dan menghapus stack sebelumnya jika perlu
                        popUpTo(DestinasiHomeEvent.route) {
                            inclusive = true  // Termasuk juga destinasi yang akan dipopUp
                        }
                    }
                    println("PengelolaHalaman: id = $id")
                }
            )
        }
        composable(DestinasiEntryEvent.route){
            EntryEventScreen(navigateBack = {
                navController.navigate(DestinasiHomeEvent.route){
                    popUpTo(DestinasiHomeEvent.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(DestinasiDetailEvent.routesWithArg) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt(DestinasiDetailEvent.ID_EVENT)

            id?.let {
                DetailEventlView(
                    IdEvent = it, // Mengirimkan ID ke DetailMhsView
                    navigateBack = {
                        // Aksi ketika tombol "Kembali" ditekan
                        navController.navigate(DestinasiHomeEvent.route) {
                            popUpTo(DestinasiHomeEvent.route) {
                                inclusive = true // Pop sampai ke DestinasiHome
                            }
                        }
                    },
                    onEditClick = {
                        // Navigasi ke halaman update dengan NIM sebagai argumen
                        navController.navigate("${DestinasiUpdateEvent.route}/$it")
                    }
                )
            }
        }


        composable(
            DestinasiUpdateEvent.routesWithArg, // Correct route with argument
            arguments = listOf(
                navArgument(DestinasiUpdateEvent.ID_EVENT) {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            // Retrieve the 'nim' argument from the navBackStackEntry
            val id_event = backStackEntry.arguments?.getInt(DestinasiUpdateEvent.ID_EVENT)

            id_event?.let {
                // Pass 'nim' to the UpdateView composable
                UpdateEventView(
                    navigateBack = {
                        navController.popBackStack()
                    },

                    modifier = modifier,

                    )
            }
        }


    }
}