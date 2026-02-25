package com.example.testgmf.shared.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlin.collections.last
import kotlin.collections.removeLastOrNull

class Navigator(
    val state: NavigationState,
    private val onBackAtRoot: (() -> Unit)? = null,
) {
    fun navigate(route: NavKey) {
        if (route in state.backStacks.keys) {
            state.topLevelRoute = route
        } else {
            state.backStacks[state.topLevelRoute]?.add(route)
        }
    }

    fun goBack() {
        val currentStack = state.backStacks[state.topLevelRoute]
            ?: error("Stack for ${state.topLevelRoute} not found")
        val currentRoute = currentStack.last()

        if (currentRoute == state.topLevelRoute) {
            if (state.topLevelRoute == state.startRoute) {
                onBackAtRoot?.invoke()
            } else {
                state.topLevelRoute = state.startRoute
            }
        } else {
            currentStack.removeLastOrNull()
        }
    }
}
