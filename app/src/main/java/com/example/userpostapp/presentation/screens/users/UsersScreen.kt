package com.example.userpostapp.presentation.screens.users

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.userpostapp.R
import com.example.userpostapp.core.utils.UiEvent
import com.example.userpostapp.domain.model.User
import com.example.userpostapp.presentation.components.ProgressDialog
import com.example.userpostapp.presentation.screens.users.components.SearchTextField
import com.example.userpostapp.presentation.screens.users.components.UserItem

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UsersScreen(
    snackbarHostState: SnackbarHostState,
    viewModel: UsersViewModel = hiltViewModel(),
    onNavigatePost: (Int, String) -> Unit
) {

    val state = viewModel.state
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = keyboardController) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                    keyboardController?.hide()
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
            SearchTextField(
                text = state.query,
                onValueChange = {
                    viewModel.onEvent(UsersEvent.OnQueryChange(it))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(
            state.users,
            key = { item: User -> item.id }
        ) { user ->
            UserItem(
                user = user,
                modifier = Modifier.fillMaxWidth()
            ) { userId, userName ->
                onNavigatePost(
                    userId,
                    userName
                )
            }
        }
    }

    if(state.isLoading) {
        ProgressDialog()
    }

    if(!state.isLoading && state.users.isEmpty()) {
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