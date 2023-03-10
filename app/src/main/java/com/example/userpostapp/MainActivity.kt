package com.example.userpostapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.userpostapp.presentation.components.TopAppBarCompose
import com.example.userpostapp.presentation.navigation.Route
import com.example.userpostapp.presentation.screens.posts.PostsScreen
import com.example.userpostapp.presentation.screens.users.UsersScreen
import com.example.userpostapp.ui.theme.UserPostAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserPostAppTheme {

                val snackbarHostState = remember { SnackbarHostState() }
                val navController = rememberNavController()

                val topApBarTitle = remember {
                    mutableStateOf("")
                }

                val topAppBarIcon = remember {
                    mutableStateOf<ImageVector?>(null)
                }

                val topAppBarOnNavigationClick = remember {
                    mutableStateOf<(() -> Unit)?>(null)
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Scaffold(
                        topBar = {
                            TopAppBarCompose(
                                title = topApBarTitle.value,
                                navigationIcon = topAppBarIcon.value,
                                onNavigationClick = topAppBarOnNavigationClick.value
                            )
                        }
                    ) { paddingValues ->
                        NavHost(
                            navController = navController,
                            startDestination = Route.USERS_SCREEN,
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            composable(Route.USERS_SCREEN) {

                                topApBarTitle.value = stringResource(id = R.string.title_topbar_home)
                                topAppBarIcon.value = Icons.Filled.Home

                                UsersScreen(snackbarHostState) { userId, userName ->
                                    navController.navigate(Route.POSTS_SCREEN +
                                            "/$userId" +
                                            "/$userName"
                                    )
                                }
                            }
                            composable(
                                Route.POSTS_SCREEN + "/{userId}/{userName}",
                                arguments = listOf(
                                    navArgument("userId") {
                                        type = NavType.IntType
                                    },
                                    navArgument("userName") {
                                        type = NavType.StringType
                                    }
                                )
                            ) {

                                topApBarTitle.value = stringResource(id = R.string.title_topbar_post)
                                topAppBarIcon.value = Icons.Filled.ArrowBack
                                topAppBarOnNavigationClick.value = {
                                    navController.popBackStack()
                                }

                                val userId = it.arguments?.getInt("userId")!!
                                val userName = it.arguments?.getString("userName")!!

                                PostsScreen(
                                    userId = userId,
                                    userName = userName,
                                    snackbarHostState = snackbarHostState
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}