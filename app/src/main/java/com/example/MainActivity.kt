package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                BypassApp()
            }
        }
    }
}

@Composable
fun BypassApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("settings") { GenericScreen("Settings", navController) }
        composable("build_app") { GenericScreen("Build App", navController) }
        composable("open_project") { GenericScreen("Open Project", navController) }
        composable("terminal") { GenericScreen("Terminal", navController) }
        composable("live_preview") { GenericScreen("Live Preview", navController) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(rootNavController: NavHostController) {
    val bottomNavController = rememberNavController()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
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
                    IconButton(onClick = { rootNavController.navigate("settings") }) {
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
                val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val items = listOf(
                    Triple("home", Icons.Default.Home, "Home"),
                    Triple("projects", Icons.Default.Folder, "Projects"),
                    Triple("files", Icons.Default.Description, "Files"),
                    Triple("dev", Icons.Default.CheckCircle, "Dev")
                )
                items.forEach { (route, icon, label) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label, fontSize = 10.sp) },
                        selected = currentRoute == route || (currentRoute == null && route == "home"),
                        onClick = {
                            if (currentRoute != route) {
                                bottomNavController.navigate(route) {
                                    popUpTo("home") { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
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
        NavHost(
            navController = bottomNavController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { HomeScreen(rootNavController) }
            composable("projects") { GenericTabScreen("Projects") }
            composable("files") { GenericTabScreen("Files") }
            composable("dev") { GenericTabScreen("Developer Tools") }
        }
    }
}

data class ChatMessage(val text: String, val isUser: Boolean)

@Composable
fun HomeScreen(rootNavController: NavHostController) {
    var text by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<ChatMessage>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        if (messages.isEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
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

            val actions = listOf(
                Pair("Build App", Icons.Default.Build) to "build_app",
                Pair("Open Project", Icons.Default.FolderOpen) to "open_project",
                Pair("Terminal", Icons.Default.Terminal) to "terminal",
                Pair("Live Preview", Icons.Default.PlayArrow) to "live_preview"
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(actions) { (action, route) ->
                    ActionCard(
                        title = action.first, 
                        icon = action.second, 
                        onClick = { rootNavController.navigate(route) }
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(messages) { message ->
                    ChatBubble(message)
                }
            }
        }

        // Prompt Input
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
                    onClick = {
                        if (text.isNotBlank()) {
                            messages.add(ChatMessage(text, isUser = true))
                            messages.add(ChatMessage("Demo mode: No AI API is configured yet. I received: \"$text\"", isUser = false))
                            text = ""
                        }
                    },
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

@Composable
fun ChatBubble(message: ChatMessage) {
    val align = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    val bgColor = if (message.isUser) BypassCyan.copy(alpha = 0.2f) else BypassDarkSurface
    val textColor = if (message.isUser) BypassCyan else Color.White
    val shape = if (message.isUser) {
        RoundedCornerShape(16.dp, 16.dp, 0.dp, 16.dp)
    } else {
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 0.dp)
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = align
    ) {
        Column(
            modifier = Modifier
                .clip(shape)
                .background(bgColor)
                .padding(16.dp)
                .widthIn(max = 280.dp)
        ) {
            Text(
                text = message.text,
                color = textColor,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            if (!message.isUser) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "DEMO MODE",
                    color = BypassCyan,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ActionCard(title: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.3f)
            .border(1.dp, BypassCardBorder, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericScreen(title: String, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BypassDarkBackground)
            )
        },
        containerColor = BypassDarkBackground
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$title Screen",
                color = BypassTextSecondary,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun GenericTabScreen(title: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$title Screen",
            color = BypassTextSecondary,
            fontSize = 18.sp
        )
    }
}
