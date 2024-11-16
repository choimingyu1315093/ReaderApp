package com.example.readerapp.screens.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.readerapp.components.InputField
import com.example.readerapp.components.ReaderAppBar
import com.example.readerapp.model.MBook
import com.example.readerapp.navigation.ReaderScreens

@Composable
fun SearchScreen(navController: NavController, modifier: Modifier = Modifier){
    Scaffold (
        topBar = {
            ReaderAppBar(
                title = "Search Books",
                icon = Icons.Default.ArrowBack,
                navController = navController,
                showProfile = false,
                onBackArrowClicked = {
                    navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                }
            )
        }
    ){ innerPadding ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column {
                SearchForm(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onSearch = {

                    }
                )
                Spacer(modifier = modifier.height(13.dp))
                BookList(navController)
            }
        }
    }
}

@Preview
@Composable
fun SearchForm(
    loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {},
    modifier: Modifier = Modifier
){
    Column {
        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember (searchQueryState.value){
            searchQueryState.value.trim().isNotEmpty()
        }

        InputField(
            valueState = searchQueryState,
            labelId = "Search",
            enabled = true,
            onAction = KeyboardActions {
                if(!valid){
                    return@KeyboardActions
                }else {
                    onSearch(searchQueryState.value.trim())
                    searchQueryState.value = ""
                    keyboardController?.hide()
                }
            }
        )
    }
}

@Composable
fun BookList(navController: NavController, modifier: Modifier = Modifier) {
    val listOfBooks = listOf(
        MBook(id = "1", title = "Hello Again", authors = "All of us", notes = null),
        MBook(id = "2", title = "Hello Again", authors = "All of us", notes = null),
        MBook(id = "3", title = "Hello Again", authors = "All of us", notes = null),
        MBook(id = "4", title = "Hello Again", authors = "All of us", notes = null)
    )

    LazyColumn (
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ){
        items(items = listOfBooks){ book ->
            BookRow(book, navController)
        }
    }
}

@Composable
fun BookRow(
    book: MBook,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(3.dp)
            .clickable {

            },
        shape = RectangleShape,
        colors = CardDefaults.cardColors(Color.White),
        elevation = CardDefaults.cardElevation(7.dp)
    ){
        Row (
            modifier = modifier
                .padding(5.dp),
            verticalAlignment = Alignment.Top
        ){
            val imageUrl = "https://db.kookje.co.kr/news2000/photo/2019/0424/L20190424.99099011755i1.jpg"
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = "book image",
                modifier = modifier
                    .width(87.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp)
            )
            Column {
                Text(
                    text = book.title.toString(),
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Author: ${book.authors}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}
