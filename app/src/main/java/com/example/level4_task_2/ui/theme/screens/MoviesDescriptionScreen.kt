package com.example.level4_task_2.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.level4_task_2.R
import com.example.level4_task_2.viewmodel.MoviesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesDescriptionScreen(
    navController: NavHostController,
    viewModel: MoviesViewModel,

    ) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    viewModel.selectedMovie?.title?.let {
                        Text(
                            text = it, color = Color(236, 235, 228)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Icon(Icons.Filled.ArrowBack, "backIcon", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(28,28,28))
            )
        },
        content = {
                innerPadding -> Description(innerPadding, viewModel)

        },
    )
}

@Composable
fun Description(modifier : PaddingValues, viewModel: MoviesViewModel){
    Column (
        modifier = Modifier.padding(modifier).background(color = Color(66, 66, 66))
    ){
        Row {
            Image(
                painter = rememberAsyncImagePainter(
                    "https://image.tmdb.org/t/p/original/" + viewModel.selectedMovie?.backdrop
                ),
                contentDescription = null,
            )
        }
        Column (
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ){
            Row {
                Image(
                    painter = rememberAsyncImagePainter(
                        "https://image.tmdb.org/t/p/original/" + viewModel.selectedMovie?.poster
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    alignment = Alignment.CenterStart
                )
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Row {
                        viewModel.selectedMovie?.title?.let { Text(text = it,
                            color = Color(236, 235, 228),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp)}
                    }
                    Row {
                        viewModel.selectedMovie?.rating?.let { Text(text = "★ " + it,
                            color = Color(236, 235, 228),
                            textAlign = TextAlign.Center) }
                    }
                }
            }
            Row (
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            ){
                Text(text = stringResource(R.string.ov),
                    color = Color(236, 235, 228),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp)
            }
            Row {
                viewModel.selectedMovie?.overview?.let { Text(text = it,
                    color = Color(236, 235, 228)) }
            }
        }

    }
}
