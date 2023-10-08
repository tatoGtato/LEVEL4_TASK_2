package com.example.level4_task_2

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.level4_task_2.ui.theme.LEVEL4_TASK_2Theme
import com.example.level4_task_2.ui.theme.screens.MoviesDescriptionScreen
import com.example.level4_task_2.ui.theme.screens.MoviesScreen
import com.example.level4_task_2.ui.theme.screens.MoviesScreens
import com.example.level4_task_2.viewmodel.MoviesViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LEVEL4_TASK_2Theme {
                Level4Task2App()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Level4Task2App() {
    val viewModel: MoviesViewModel = viewModel()
    val navController = rememberNavController()

    MoviesNavHost(navController)

}


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
private fun MoviesNavHost(
    navController: NavHostController,
) {
    val viewModel: MoviesViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = MoviesScreens.Movies.name
    ){

        composable(MoviesScreens.Movies.name) {
            viewModel.getMovie()
            MoviesScreen(navController, viewModel)
        }
        composable(MoviesScreens.MovieDescription.name) {
            MoviesDescriptionScreen(navController, viewModel)
        }
    }
}



