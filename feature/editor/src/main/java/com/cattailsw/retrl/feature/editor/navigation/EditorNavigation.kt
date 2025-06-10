package com.cattailsw.retrl.feature.editor.navigation // Updated package

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.cattailsw.retrl.feature.editor.ui.EditorScreen // Updated import

const val EDITOR_ROUTE_PATTERN = "editor_route"
const val SESSION_ID_ARG = "sessionId"
const val editorRouteWithArg = "${EDITOR_ROUTE_PATTERN}?${SESSION_ID_ARG}={${SESSION_ID_ARG}}"

// Navigation function to navigate to the editor screen
fun NavController.navigateToEditor(sessionId: String? = null, navOptions: NavOptions? = null) {
    val route = if (sessionId != null) {
        "${EDITOR_ROUTE_PATTERN}?${SESSION_ID_ARG}=${sessionId}"
    } else {
        EDITOR_ROUTE_PATTERN
    }
    this.navigate(route, navOptions)
}

// Extension function on NavGraphBuilder to define the editor screen composable
fun NavGraphBuilder.editorScreen(
    // Callback for when the editor wants to navigate to another destination
    // e.g., to an export screen, settings, or back
    onNavigateToSomewhere: (String) -> Unit
    // Add other callbacks as needed, e.g., onShowSnackbar: (String) -> Unit
) {
    composable(
        route = editorRouteWithArg,
        arguments = listOf(
            navArgument(SESSION_ID_ARG) {
                type = NavType.StringType
                nullable = true
                // defaultValue = null // Explicitly null if that's the intent for new session
            }
        )
    ) { backStackEntry ->
        val sessionId = backStackEntry.arguments?.getString(SESSION_ID_ARG)
        EditorScreen(
            sessionId = sessionId,
            onNavigateFurther = onNavigateToSomewhere // Example of using the callback
            // Pass other necessary parameters to EditorScreen
        )
    }
}
