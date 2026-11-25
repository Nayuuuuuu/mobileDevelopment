package com.nyp.scaffoldingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.annotation.DrawableRes
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.nyp.scaffoldingapp.ui.pages.*
import com.nyp.scaffoldingapp.ui.theme.ScaffoldingTheme
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.saveable.rememberSaveableStateHolder

/**
 * The four bottom tabs. An enum keeps the list in one place — add a tab here
 * and the navigation bar updates itself.
 */
enum class Tab(val label: String, @DrawableRes val icon: Int) {
    EXERCISES("Exercises", R.drawable.ic_home),
    LOG("Log", R.drawable.ic_email),
    NEARBY("Nearby", R.drawable.ic_list),
    PROGRESS("Progress", R.drawable.ic_star),
    PROFILE("Profile", R.drawable.ic_list)
}

enum class Screen { SPLASH, SIGN_IN, SIGN_UP, MAIN }

@Composable
fun AppRoot() {
    var screen by rememberSaveable { mutableStateOf(Screen.SPLASH) }

    when (screen) {
        Screen.SPLASH -> SplashScreen(
            onFinished = { isLoggedIn ->
                screen = if (isLoggedIn) Screen.MAIN else Screen.SIGN_IN
            }
        )
        Screen.SIGN_IN -> SignInScreen(
            onSignIn = { _, _ -> screen = Screen.MAIN },          // TODO: real login check
            onSignUp = { screen = Screen.SIGN_UP },
            onContinueAsGuest = { screen = Screen.MAIN }
        )
        Screen.SIGN_UP -> {
            BackHandler { screen = Screen.SIGN_IN }               // back goes to Sign in, not out of the app
            SignUpScreen(
                onSignUp = { _, _, _, _ -> screen = Screen.MAIN }, // TODO: save the account
                onBackToSignIn = { screen = Screen.SIGN_IN }
            )
        }
        Screen.MAIN -> AppScaffold()
    }
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScaffoldingTheme {
                AppRoot()
            }
        }
    }
}

@Composable
fun AppScaffold() {
    // rememberSaveable, not remember: the selected tab must survive a rotation.
    var selectedTab by rememberSaveable { mutableStateOf(Tab.EXERCISES) }
    val stateHolder = rememberSaveableStateHolder()

    Scaffold(
        bottomBar = {
            NavigationBar {
                Tab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = {
                            // Icon() tints the image with the theme colour,
                            // which is what gives you selected / unselected
                            // states for free. Use Image() instead if you
                            // want the picture's own colours.
                            Icon(
                                painter = painterResource(tab.icon),
                                contentDescription = tab.label
                            )
                        },
                        label = { Text(tab.label) }
                    )
                }
            }
        }
    ) { padding ->
        // Use the padding Scaffold gives you, or your content hides behind
        // the navigation bar.
        Box(Modifier.padding(padding).fillMaxSize()) {
            stateHolder.SaveableStateProvider(selectedTab.name) {
                when (selectedTab) {
                    Tab.EXERCISES -> ExercisesScreen()
                    Tab.LOG -> LogScreen()
                    Tab.NEARBY -> NearbyScreen()
                    Tab.PROGRESS -> ProgressScreen()
                    Tab.PROFILE -> ProfileScreen()
                }
            }
        }
    }
}
