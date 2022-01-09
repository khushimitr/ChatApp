package com.example.chatappui.presentation.username

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.chatappui.ui.theme.sendChat
import kotlinx.coroutines.flow.collectLatest

@Composable
fun UsernameScreen(
    viewModel: UsernameViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit
) {

    LaunchedEffect(key1 = true) {
        viewModel.onJoinChat.collectLatest { username ->
            onNavigate("chat_screen/$username")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = viewModel.usernameText.value,
                onValueChange = viewModel::onUsernameChange,
                textStyle = TextStyle(color = Color.White),
                label = {
                    Text(text = "Enter a username...", color = Color.White)
                },

                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            val context = LocalContext.current
            Button(
                onClick = {
                    if(viewModel.usernameText.value.isBlank())
                    {
                        Toast.makeText(context, "Username Field is empty", Toast.LENGTH_SHORT).show()
                    }
                    viewModel.onJoinClicked()
                },
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Join")
            }
        }
    }
}