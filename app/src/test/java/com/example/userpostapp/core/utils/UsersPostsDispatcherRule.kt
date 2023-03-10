@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.userpostapp.core.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class UsersPostsDispatcherRule @OptIn(ExperimentalCoroutinesApi::class) constructor(
    private val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        return Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        return Dispatchers.resetMain()
    }
}