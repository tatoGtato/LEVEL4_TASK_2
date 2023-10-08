package com.example.level4_task_2.ui.theme.screens

import android.util.Log
import android.util.Patterns
import android.webkit.URLUtil
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.level4_task_2.R
import com.example.level4_task_2.data.api.Api
import com.example.level4_task_2.data.api.util.Resource
import com.example.level4_task_2.data.model.Mov
import com.example.level4_task_2.data.model.Movie
import com.example.level4_task_2.viewmodel.MoviesViewModel

//89ee32e6c536be7054576c435aa7b363 Log.d("myTag", "This is my message")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesScreen(
    navController: NavHostController,
    viewModel: MoviesViewModel,

    ) {
    val moviesResource: Resource<Movie>? by viewModel.MoviesResource.observeAsState()
    val localDensity = LocalDensity.current
    var HeightDp by remember { mutableStateOf(0.dp) }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.onGloballyPositioned { coordinates ->
                    HeightDp = with(localDensity) { coordinates.size.height.toDp() }},
                title = {
                    moviesResource?.let { SearchView(viewModel, it) }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(28,28,28))
            )
        },
        content = {
                innerPadding ->
                when (moviesResource) {
                    is Resource.Success -> {
                        Column (
                            modifier = Modifier.fillMaxSize().background(Color(66, 66, 66))

                        ){
                            moviesResource?.let { MovieGrid(it, navController, viewModel, innerPadding) }
                            moviesResource?.data?.results?.get(0)?.title.let {
                                if (it != null) {
                                    Text(text = it)
                                }
                            }
                        }
                    }

                    is Resource.Error -> {
                        Column (
                            modifier = Modifier
                                .fillMaxSize().background(Color(66, 66, 66))
                                .padding(innerPadding),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            Row {
                                Text(text = stringResource(R.string.wrong))
                            }
                        }
                    }

                    is Resource.Loading -> {
                        Column (
                            modifier = Modifier
                                .fillMaxSize().background(Color(66, 66, 66))
                                .padding(innerPadding),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            var loading by remember { mutableStateOf(false) }

                            Button(onClick = { loading = true }, enabled = !loading) {
                                Text("Start loading")
                            }

                            if (!loading)

                                CircularProgressIndicator(
                                    modifier = Modifier.width(64.dp),
                                    color = (Color(236, 235, 228)),
                                )
                        }
                    }
                    else -> {
                        Column (
                            modifier = Modifier
                                .fillMaxSize().background(Color(66, 66, 66))
                                .padding(innerPadding),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            Row {
                                Text(text = stringResource(R.string.wrong))
                            }
                        }
                    }
            }
        },
    )

}

@Composable
fun MovieGrid(moviesResource : Resource<Movie>, navController: NavHostController, viewModel: MoviesViewModel, modifier : PaddingValues){
    val list: List<Int> = listOf(0)
    if (viewModel.searchQueryState.value == ""){
        Column (
            modifier = Modifier.background(Color(66, 66, 66))
                .fillMaxSize()
                .padding(modifier),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(text = stringResource(R.string.fill), color = Color(236, 235, 228))
        }
    }
    else{
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxHeight().background(Color(66, 66, 66))
                .padding(modifier),
            columns = GridCells.Adaptive(128.dp),
            content = {
                items(viewModel.query) { item ->
                    Box (
                        modifier = Modifier
                            .fillMaxSize().border(width = 2.dp, color = Color(66, 66, 66)),
                        contentAlignment = Alignment.Center
                    ){
                        Image(
                            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/original/" + item.poster),
                            contentDescription = null,
                            modifier = Modifier
                                .size(200.dp)
                                .clickable {
                                    navController.navigate(MoviesScreens.MovieDescription.name)
                                    viewModel.selectedMovie = item
                                }
                        )
                    }
                }
            }
        )
    }
}


@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchView(viewModel: MoviesViewModel, moviesResource : Resource<Movie>) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = viewModel.searchQueryState.value,
        onValueChange = { value ->
            viewModel.searchQueryState.value = value
            moviesResource.data?.let { viewModel.searchFilter(it.results) }
        },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            IconButton(onClick = {
                Log.d("DASKHJBFKAS", viewModel.query.size.toString())
                //based on @ExperimentalComposeUiApi - if this doesn't work in a newer version remove it
                //no alternative in compose for hiding keyboard at time of writing
                keyboardController?.hide()
            }) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(8.dp)
                        .size(24.dp),
                    tint = Color.White
                )
            }
        },
        trailingIcon = {
            if (viewModel.searchQueryState.value != "") {
                IconButton(
                    onClick = {
                        viewModel.resetQuery() // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(8.dp)
                            .size(24.dp),
                        tint = Color.White
                    )
                }
            }
        },
        placeholder = {
            Text(
                text = stringResource(R.string.search_movie_hint),
                color = Color.White
            )
        },
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color(218, 221, 216),
            cursorColor = Color(218, 221, 216),
            containerColor = Color(28,28,28),
            focusedIndicatorColor = Color(218, 221, 216),
            unfocusedIndicatorColor = Color(218, 221, 216),
            disabledIndicatorColor = Color(218, 221, 216),
        )
    )
}