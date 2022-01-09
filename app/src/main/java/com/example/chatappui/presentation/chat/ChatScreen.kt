package com.example.chatappui.presentation.chat

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.chatappui.ui.theme.background
import com.example.chatappui.ui.theme.revChat
import com.example.chatappui.ui.theme.sendChat
import com.example.chatappui.ui.theme.timeColor
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ChatScreen(
    username: String?,
    viewModel: ChatViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.toastEvent.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    // IMITATING ON START
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                viewModel.connectToChat()
            } else if (event == Lifecycle.Event.ON_STOP) {
                viewModel.disconnect()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


    val state = viewModel.chatState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            item {
                Spacer(modifier = Modifier.height(32.dp))
            }
            items(state.messages) { message ->
                val isOwnMessage = message.username == username

                Box(
                    contentAlignment = if (isOwnMessage) {
                        Alignment.CenterEnd
                    } else {
                        Alignment.CenterStart
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(
                            start = if (isOwnMessage) {
                                20.dp
                            } else {
                                0.dp
                            },
                            end = if (isOwnMessage) {
                                0.dp
                            } else {
                                20.dp
                            }
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(
                                    color = if (isOwnMessage) sendChat else revChat,
                                    shape = if (isOwnMessage) {
                                        RoundedCornerShape(
                                            topStart = 10.dp,
                                            topEnd = 10.dp,
                                            bottomEnd = 0.dp,
                                            bottomStart = 10.dp
                                        )
                                    } else {
                                        RoundedCornerShape(
                                            topStart = 10.dp,
                                            topEnd = 10.dp,
                                            bottomEnd = 10.dp,
                                            bottomStart = 0.dp
                                        )
                                    }
                                )
                                .padding(10.dp)
                        ) {
                            if (!isOwnMessage) {
                                Text(
                                    text = message.username,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(3.dp))
                            }

                            Text(
                                text = message.text,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = message.formattedTime,
                            color = timeColor,
                            fontSize = 10.sp,
                            modifier = Modifier.align(Alignment.End)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = viewModel.messageText.value,
                onValueChange = viewModel::onMessageChange,
                textStyle = TextStyle(color = Color.White),
                label = {
                    Text(text = "Enter a Message", color = Color.White)
                },
                modifier = Modifier.weight(1f),
                trailingIcon = {
                    IconButton(onClick = {
                        viewModel.sendMessage()
                        viewModel.onMessageChange("")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = sendChat
                        )
                    }
                }
            )
        }
    }
}