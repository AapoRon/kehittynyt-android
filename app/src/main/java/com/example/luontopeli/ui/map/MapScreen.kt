package com.example.luontopeli.ui.map

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.luontopeli.viewmodel.WalkViewModel

@Composable
fun MapScreen(
    walkViewModel: WalkViewModel = hiltViewModel()
) {
    val steps by walkViewModel.steps.collectAsState()
    val distance by walkViewModel.distance.collectAsState()
    val isWalking by walkViewModel.isWalking.collectAsState()

    // Pyydä ACTIVITY_RECOGNITION-lupa Android 10+ (askelmittaria varten)
    val activityRecognitionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* granted */ }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            activityRecognitionLauncher.launch(Manifest.permission.ACTIVITY_RECOGNITION)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Karttaplaceholder
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Filled.Map,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                )
                Text("Karttanäkymä (Viikko 3)", color = Color.Gray)
            }
        }

        // Stats-paneeli
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Askeleet",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$steps",
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Column {
                    Text(
                        text = "Matka",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = String.format("%.1f m", distance),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
                Button(
                    onClick = { 
                        if (isWalking) walkViewModel.stopWalk() else walkViewModel.startWalk() 
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isWalking) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(Icons.Filled.DirectionsWalk, contentDescription = null)
                    Spacer(Modifier.width(4.dp))
                    Text(if (isWalking) "Lopeta" else "Aloita")
                }
            }
        }
    }
}
