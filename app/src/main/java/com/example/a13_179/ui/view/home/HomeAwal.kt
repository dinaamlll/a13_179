package com.example.a13_179.ui.view.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a13_179.R
import com.example.a13_179.ui.navigation.DestinasiNavigasi

object DestinasiHomeUtama: DestinasiNavigasi {
    override val route ="home_utama"
    override val titleRes = "Kelola Event"
}

@Composable
fun HomeUtamaScreen(
    onEventClick: () -> Unit,
    onPesertaClick: () -> Unit,
    onTiketClick: () -> Unit,
    onTransaksiClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFE0F0)) // Background pink soft
            .padding(16.dp)
    ) {
        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TampilanHeader()
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Kelola Event",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFAD0D7A), // Dark Pink color
                modifier = Modifier.padding(bottom = 20.dp) // Add space between the text and the buttons
            )

            AnimatedButton(
                text = "Event",
                icon = Icons.Filled.Create,
                onClick = onEventClick,
                buttonColor = Color(0xFFAD0D7A) // Dark pink for button
            )
            Spacer(modifier = Modifier.height(20.dp))

            AnimatedButton(
                text = "Peserta",
                icon = Icons.Filled.AccountCircle,
                onClick = onPesertaClick,
                buttonColor = Color(0xFFAD0D7A)
            )
            Spacer(modifier = Modifier.height(20.dp))

            AnimatedButton(
                text = "Tiket",
                icon = Icons.Filled.DateRange,
                onClick = onTiketClick,
                buttonColor = Color(0xFFAD0D7A)
            )
            Spacer(modifier = Modifier.height(20.dp))

            AnimatedButton(
                text = "Transaksi",
                icon = Icons.Filled.Notifications,
                onClick = onTransaksiClick,
                buttonColor = Color(0xFFAD0D7A)
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun TampilanHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFF69B4)) // Pink header
            .padding(20.dp)
            .shadow(4.dp, RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            // Hapus bagian Box dan Image untuk menghilangkan gambar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally, // Menempatkan teks di tengah
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Kelola Event",
                    color = Color.White,
                    fontSize = 28.sp, // Increased font size for better readability
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(3.dp))
                Text(
                    text = "Event Management Made Easy",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}


@Composable
fun AnimatedButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    buttonColor: Color
) {
    var isPressed by remember { mutableStateOf(false) }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(50.dp), // Rounded corners for buttons
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .height(60.dp)
            .fillMaxWidth(0.9f)
            .clickable { isPressed = !isPressed }
            .shadow(
                8.dp,
                RoundedCornerShape(50.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(40.dp),
                tint = Color.White
            )
            Text(
                text = text,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.animateScale(isPressed)
            )
        }
    }
}

@Composable
fun Modifier.animateScale(isPressed: Boolean): Modifier {
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.1f else 1.0f,
        animationSpec = tween(durationMillis = 300)
    )
    return this.then(Modifier.scale(scale))
}
