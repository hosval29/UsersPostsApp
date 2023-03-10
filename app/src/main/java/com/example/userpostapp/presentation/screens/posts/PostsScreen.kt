package com.example.userpostapp.presentation.screens.posts

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.userpostapp.R
import com.example.userpostapp.core.utils.UiEvent
import com.example.userpostapp.domain.model.Post
import com.example.userpostapp.presentation.components.ProgressDialog
import com.example.userpostapp.presentation.screens.posts.components.PostItem

@Composable
fun PostsScreen(
    userId: Int,
    userName: String,
    snackbarHostState: SnackbarHostState,
    viewModel: PostsViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val context = LocalContext.current
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(userId) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getUserPostByUserId(userId)
        }
    }

    LaunchedEffect(context) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                else -> Unit
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        item {
            Text(
                text = "Estas son tus publicaciones",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = userName,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(
            state.posts,
            key = { item: Post -> item.title }
        ) { post ->
            PostItem(
                post = post,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (state.isLoading) {
        ProgressDialog()
    }

    if (!state.isLoading && state.posts.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.empty_list),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color(0xFF004A50)
            )
        }
    }
}