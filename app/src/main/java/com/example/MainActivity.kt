package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                BypassAppScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BypassAppScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Custom Logo placeholder
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(BypassDarkSurface)
                                .border(1.dp, BypassCyan.copy(alpha = 0.5f), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Code,
                                contentDescription = "Logo",
                                tint = BypassCyan,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Bypass AI",
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 18.sp
                            )
                            Text(
                                text = "AI Developer Assistant",
                                fontSize = 12.sp,
                                color = BypassTextSecondary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BypassDarkBackground
                ),
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = BypassTextSecondary)
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = BypassDarkSurface,
                contentColor = BypassTextSecondary,
                tonalElevation = 0.dp
            ) {
                val items = listOf(
                    Triple("Home", Icons.Default.Home, true),
                    Triple("Projects", Icons.Default.Folder, false),
                    Triple("Files", Icons.Default.Description, false),
                    Triple("Dev", Icons.Default.CheckCircle, false)
                )
                items.forEach { (name, icon, selected) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = name) },
                        label = { Text(name, fontSize = 10.sp) },
                        selected = selected,
                        onClick = { },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = BypassCyan,
                            selectedTextColor = BypassCyan,
                            indicatorColor = BypassCyan.copy(alpha = 0.15f),
                            unselectedIconColor = BypassTextSecondary,
                            unselectedTextColor = BypassTextSecondary
                        )
                    )
                }
            }
        },
        containerColor = BypassDarkBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            // Welcome Section
            Text(
                text = "What can I build for you?",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "I am your AI Developer Assistant. I can help build native Android apps, architect full-stack code, and execute tasks autonomously.",
                style = MaterialTheme.typography.bodyMedium,
                color = BypassTextSecondary,
                lineHeight = 22.sp
            )
            
            Spacer(modifier = Modifier.height(32.dp))

            // Quick Actions
            val actions = listOf(
                Pair("Build App", Icons.Default.Build),
                Pair("Open Project", Icons.Default.FolderOpen),
                Pair("Terminal", Icons.Default.Terminal),
                Pair("Live Preview", Icons.Default.PlayArrow)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(actions) { action ->
                    ActionCard(title = action.first, icon = action.second)
                }
            }

            // Prompt Input
            var text by remember { mutableStateOf("") }
            
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(BypassDarkSurface)
                    .border(1.dp, BypassCardBorder, RoundedCornerShape(24.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        placeholder = { Text("Ask Bypass AI anything...", color = BypassTextSecondary) },
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            cursorColor = BypassCyan
                        ),
                        singleLine = true
                    )
                    
                    IconButton(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .clip(CircleShape)
                            .background(BypassCyan.copy(alpha = 0.1f))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = BypassCyan,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ActionCard(title: String, icon: ImageVector) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.3f)
            .border(1.dp, BypassCardBorder, RoundedCornerShape(16.dp))
            .clickable { /*TODO*/ },
        colors = CardDefaults.cardColors(containerColor = BypassDarkSurface),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(BypassDarkBackground),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = BypassCyan,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}
