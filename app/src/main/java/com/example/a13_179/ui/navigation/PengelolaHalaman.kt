package com.example.a13_179.ui.navigation



import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a13_179.ui.import.DestinasiEntryTransaksi
import com.example.a13_179.ui.import.EntryTransaksiScreen
import com.example.a13_179.ui.view.event.DestinasiDetailEvent
import com.example.a13_179.ui.view.event.DestinasiEntryEvent
import com.example.a13_179.ui.view.event.DestinasiHomeEvent
import com.example.a13_179.ui.view.event.DestinasiUpdateEvent
import com.example.a13_179.ui.view.event.DetailEventScreen
import com.example.a13_179.ui.view.event.EntryEventScreen
import com.example.a13_179.ui.view.event.HomeEventScreen
import com.example.a13_179.ui.view.event.UpdateEventScreen
import com.example.a13_179.ui.view.home.DestinasiHomeUtama
import com.example.a13_179.ui.view.home.HomeUtamaScreen
import com.example.a13_179.ui.view.peserta.DestinasiDetailPeserta
import com.example.a13_179.ui.view.peserta.DestinasiEntryPeserta
import com.example.a13_179.ui.view.peserta.DestinasiHomePeserta
import com.example.a13_179.ui.view.peserta.DestinasiUpdatePeserta
import com.example.a13_179.ui.view.peserta.DetailPesertaScreen
import com.example.a13_179.ui.view.peserta.EntryPesertaScreen
import com.example.a13_179.ui.view.peserta.HomePesertaScreen
import com.example.a13_179.ui.view.peserta.UpdatePesertaScreen
import com.example.a13_179.ui.view.tiket.DestinasiDetailTiket
import com.example.a13_179.ui.view.tiket.DestinasiEntryTiket
import com.example.a13_179.ui.view.tiket.DestinasiHomeTiket
import com.example.a13_179.ui.view.tiket.DestinasiUpdateTiket
import com.example.a13_179.ui.view.tiket.DetailTiketView
import com.example.a13_179.ui.view.tiket.EntryTiketScreen
import com.example.a13_179.ui.view.tiket.HomeTiketScreen
import com.example.a13_179.ui.view.tiket.UpdateTiketView
import com.example.a13_179.ui.view.transaksi.DestinasiDetailTransaksi
import com.example.a13_179.ui.view.transaksi.DestinasiHomeTransaksi
import com.example.a13_179.ui.view.transaksi.DetailTransaksiView
import com.example.a13_179.ui.view.transaksi.HomeTransaksiScreen


@Composable
fun PengelolaHalaman(navController: NavHostController = rememberNavController(), modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeUtama.route,
        modifier = Modifier,
    ) {
        composable(DestinasiHomeUtama.route) {
            HomeUtamaScreen(
                onEventClick = {
                    navController.navigate(DestinasiHomeEvent.route) },
                onPesertaClick = {
                    navController.navigate(DestinasiHomePeserta.route) },
                onTiketClick = {
                    navController.navigate(DestinasiHomeTiket.route) },
                onTransaksiClick = {
                    navController.navigate(DestinasiHomeTransaksi.route) }
            )
        }
        composable(DestinasiHomeEvent.route) {
            HomeEventScreen(
                navigateToItemEntry = {
                    navController.navigate(DestinasiEntryEvent.route) },
                onDetailClick = {
                    id_event ->
                    navController.navigate("detail/$id_event")
                },
                onEventClick = { navController.navigate(DestinasiHomeEvent.route) },
                onPesertaClick = { navController.navigate(DestinasiHomePeserta.route) },
                onTiketClick = { navController.navigate(DestinasiHomeTiket.route) },
                onTransaksiClick = { navController.navigate(DestinasiHomeTransaksi.route) }
            )
        }
        composable(DestinasiEntryEvent.route) {
            EntryEventScreen(
                onEventClick = {
                    navController.navigate(DestinasiHomeEvent.route) },
                onPesertaClick = {
                    navController.navigate(DestinasiHomePeserta.route) },
                onTiketClick = {
                    navController.navigate(DestinasiHomeTiket.route) },
                onTransaksiClick = {
                    navController.navigate(DestinasiHomeTransaksi.route) },
                navigateBack = {
                    navController.navigate(DestinasiHomeEvent.route) {
                        popUpTo(DestinasiHomeEvent.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(
            route = "detail/{id_event}",
            arguments = listOf(
                navArgument("id_event") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_event = backStackEntry.arguments?.getInt(DestinasiDetailEvent.id_event)
            id_event?.let {
                DetailEventScreen(
                    id_event = it,
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateEvent.route}/$id_event")
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEventClick = {
                        navController.navigate(DestinasiHomeEvent.route) },
                    onPesertaClick = {
                        navController.navigate(DestinasiHomePeserta.route) },
                    onTiketClick = {
                        navController.navigate(DestinasiHomeTiket.route) },
                    onTransaksiClick = {
                        navController.navigate(DestinasiHomeTransaksi.route) }
                )
            }
        }
        composable(
            route = "${DestinasiUpdateEvent.route}/{id_event}",
            arguments = listOf(
                navArgument("id_event") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_event = backStackEntry.arguments?.getInt("id_event")
            id_event?.let {
                UpdateEventScreen(
                    onBackClick = {
                        navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeEvent.route) {
                            popUpTo(DestinasiHomeEvent.route) { inclusive = true }
                        }
                    },
                    onEventClick = {
                        navController.navigate(DestinasiHomeEvent.route) },
                    onPesertaClick = {
                        navController.navigate(DestinasiHomePeserta.route) },
                    onTiketClick = {
                        navController.navigate(DestinasiHomeTiket.route) },
                    onTransaksiClick = {
                        navController.navigate(DestinasiHomeTransaksi.route) }
                )
            }
        }
        composable(DestinasiHomePeserta.route) {
            HomePesertaScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryPeserta.route) },
                onDetailClick = { id_peserta ->
                    navController.navigate("detail_peserta/$id_peserta")


                },
                onEventClick = {
                    navController.navigate(DestinasiHomeEvent.route) },
                onPesertaClick = {
                    navController.navigate(DestinasiHomePeserta.route) },
                onTiketClick = {
                    navController.navigate(DestinasiHomeTiket.route) },
                onTransaksiClick = {
                    navController.navigate(DestinasiHomeTransaksi.route) }
            )
        }
        composable(
            DestinasiEntryPeserta.route) {
            EntryPesertaScreen(
                navigateBack = {
                    navController.navigate(
                        DestinasiHomePeserta.route) {
                        popUpTo(
                            DestinasiHomePeserta.route) {
                            inclusive = true
                        }
                    }
                },
                onEventClick = {
                    navController.navigate(DestinasiHomePeserta.route) },
                onPesertaClick = {
                    navController.navigate(DestinasiHomePeserta.route) },
                onTiketClick = {
                    navController.navigate(DestinasiHomeTiket.route) },
                onTransaksiClick = {
                    navController.navigate(DestinasiHomeTransaksi.route) }
            )
        }
        composable(
            route = "detail_peserta/{id_peserta}",
            arguments = listOf(
                navArgument("id_peserta") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_peserta = backStackEntry.arguments?.getInt(DestinasiDetailPeserta.id_peserta)
            id_peserta?.let {
                DetailPesertaScreen(
                    id_peserta = it,
                    onEditClick = {
                        navController.navigate(
                            "${DestinasiUpdatePeserta.route}/$id_peserta")
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEventClick = {
                        navController.navigate(DestinasiHomeEvent.route) },
                    onPesertaClick = { navController.navigate(DestinasiHomePeserta.route) },
                    onTiketClick = { navController.navigate(DestinasiHomeTiket.route) },
                    onTransaksiClick = {
                        navController.navigate(DestinasiHomeTransaksi.route) }
                )
            }
        }
        composable(
            route = "${DestinasiUpdatePeserta.route}/{id_peserta}",
            arguments = listOf(
                navArgument("id_peserta") {
                    type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_peserta = backStackEntry.arguments?.getInt("id_peserta")
            id_peserta?.let {
                UpdatePesertaScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomePeserta.route) {
                            popUpTo(DestinasiHomePeserta.route) { inclusive = true }
                        }
                    },
                    onEventClick = {
                        navController.navigate(DestinasiHomeEvent.route) },
                    onPesertaClick = { navController.navigate(DestinasiHomePeserta.route) },
                    onTiketClick = { navController.navigate(DestinasiHomeTiket.route) },
                    onTransaksiClick = { navController.navigate(DestinasiHomeTransaksi.route) }
                )
            }
        }
        composable(DestinasiHomeTiket.route) {
            HomeTiketScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryTiket.route) },
                onDetailClick = { id_tiket ->
                    navController.navigate("detail_tiket/$id_tiket")
                    Log.d("NavigationDebug", "Navigating to detail/$id_tiket")
                },
                onEventClick = { navController.navigate(DestinasiHomeEvent.route) },
                onPesertaClick = { navController.navigate(DestinasiHomePeserta.route) },
                onTiketClick = {
                    navController.navigate(DestinasiHomeTiket.route) },
                onTransaksiClick = { navController.navigate(DestinasiHomeTransaksi.route) }
            )
        }
        composable(DestinasiEntryTiket.route) {
            EntryTiketScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeTiket.route) {
                        popUpTo(DestinasiHomeTiket.route) {
                            inclusive = true
                        }
                    }
                },
                onEventClick = {
                    navController.navigate(DestinasiHomeEvent.route) },
                onPesertaClick = {
                    navController.navigate(DestinasiHomePeserta.route) },
                onTiketClick = {
                    navController.navigate(DestinasiHomeTiket.route) },
                onTransaksiClick = { navController.navigate(DestinasiHomeTransaksi.route) }
            )
        }
        composable(
            route = "detail_tiket/{id_tiket}",
            arguments = listOf(
                navArgument("id_tiket") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_tiket = backStackEntry.arguments?.getInt(DestinasiDetailTiket.id_tiket)
            id_tiket?.let {
                DetailTiketView(
                    id_tiket = it,
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateTiket.route}/$id_tiket")
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEventClick = {
                        navController.navigate(DestinasiHomeEvent.route) },
                    onPesertaClick = {
                        navController.navigate(DestinasiHomePeserta.route) },
                    onTiketClick = { navController.navigate(DestinasiHomeTiket.route) },
                    onTransaksiClick = { navController.navigate(DestinasiHomeTransaksi.route) }
                )
            }
        }
        composable(DestinasiUpdateTiket.routesWithArg,
            arguments = listOf(
                navArgument("id_tiket") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_tiket = backStackEntry.arguments?.getInt(DestinasiUpdateTiket.id_tiket)
            id_tiket?.let {
                UpdateTiketView(
                    onBack = { navController.popBackStack() },
                    onNavigate = {
                        navController.navigate(DestinasiHomeTiket.route) {
                            popUpTo(DestinasiHomeTiket.route) { inclusive = true }
                        }
                    },
                    onEventClick = { navController.navigate(DestinasiHomeEvent.route) },
                    onPesertaClick = { navController.navigate(DestinasiHomePeserta.route) },
                    onTiketClick = { navController.navigate(DestinasiHomeTiket.route) },
                    onTransaksiClick = { navController.navigate(DestinasiHomeTransaksi.route) }
                )
            }
        }

        composable(DestinasiHomeTransaksi.route) {
            HomeTransaksiScreen(
                navigateToItemEntry = { navController.navigate(DestinasiEntryTransaksi.route) },
                onDetailClick = { id_transaksi ->
                    navController.navigate("detail_transaksi/$id_transaksi")

                },
                onEventClick = { navController.navigate(DestinasiHomeEvent.route) },
                onPesertaClick = { navController.navigate(DestinasiHomePeserta.route) },
                onTiketClick = { navController.navigate(DestinasiHomeTiket.route) },
                onTransaksiClick = { navController.navigate(DestinasiHomeTransaksi.route) }
            )
        }
        composable(DestinasiEntryTransaksi.route) {
            EntryTransaksiScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeTransaksi.route) {
                        popUpTo(DestinasiHomeTransaksi.route) {
                            inclusive = true
                        }
                    }
                },
                onEventClick = { navController.navigate(DestinasiHomeEvent.route) },
                onPesertaClick = { navController.navigate(DestinasiHomePeserta.route) },
                onTiketClick = { navController.navigate(DestinasiHomeTiket.route) },
                onTransaksiClick = { navController.navigate(DestinasiHomeTransaksi.route) }
            )
        }
        composable(
            route = "detail_transaksi/{id_transaksi}",
            arguments = listOf(
                navArgument("id_transaksi") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val id_transaksi =
                backStackEntry.arguments?.getInt(DestinasiDetailTransaksi.id_transaksi)
            id_transaksi?.let {
                DetailTransaksiView(
                    id_transaksi = it,
                    onEditClick = {
                        navController.navigate("update_transaksi/$id_transaksi")
                    },
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onEventClick = { navController.navigate(DestinasiHomeEvent.route) },
                    onPesertaClick = { navController.navigate(DestinasiHomePeserta.route) },
                    onTiketClick = { navController.navigate(DestinasiHomeTiket.route) },
                    onTransaksiClick = { navController.navigate(DestinasiHomeTransaksi.route) }
                )
            }
        }
    }
}