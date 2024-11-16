package com.example.readerapp.screens.home

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.readerapp.R
import com.example.readerapp.ReaderApp
import com.example.readerapp.components.FABContent
import com.example.readerapp.components.ListCard
import com.example.readerapp.components.ReaderAppBar
import com.example.readerapp.components.TitleSection
import com.example.readerapp.model.MBook
import com.example.readerapp.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@Preview
@Composable
fun Home(navController: NavController = NavController(LocalContext.current), modifier: Modifier = Modifier){
    Scaffold (
        topBar = {
            ReaderAppBar(title = "A. Reader", navController = navController)
        },
        floatingActionButton = {
            FABContent(onTap = {
                navController.navigate(ReaderScreens.SearchScreen.name)
            })
        }
    ){ innerPadding ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            HomeContent(navController = navController)
        }
    }
}

@Composable
fun HomeContent(navController: NavController, modifier: Modifier = Modifier){
    val listOfBooks = listOf(
        MBook(id = "1", title = "Hello Again", authors = "All of us", notes = null),
        MBook(id = "2", title = "Hello Again", authors = "All of us", notes = null),
        MBook(id = "3", title = "Hello Again", authors = "All of us", notes = null),
        MBook(id = "4", title = "Hello Again", authors = "All of us", notes = null)
    )

    val currentUserName = if(!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
         FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
    }else {
         "N/A"
    }

    Column (
        verticalArrangement = Arrangement.Top,
        modifier = modifier
            .padding(2.dp)
    ){
        Row (
            modifier = modifier
                .align(alignment = Alignment.Start)
        ){
            TitleSection(label = "Your reading \n "+" activity right now...")
            Spacer(modifier = modifier.fillMaxWidth(.7f))
            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = modifier
                        .clickable {
                            navController.navigate(ReaderScreens.ReaderStatsScreen.name)
                        }
                        .size(45.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = currentUserName!!,
                    modifier = modifier
                        .padding(2.dp),
                    style = TextStyle(
                        color = Color.Red,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                Divider()
            }
        }
        ReadingRightNowArea(
            books = listOf(),
            navController = navController
        )
        TitleSection(label = "Reading List")
        BookListArea(
            listOfBooks = listOfBooks,
            navController = navController
        )
    }
}

@Composable
fun ReadingRightNowArea(
    books: List<MBook>,
    navController: NavController,
    modifier: Modifier = Modifier
){
    ListCard()
}

@Composable
fun BookListArea(
    listOfBooks: List<MBook>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    HorizontalScrollableComponent(listOfBooks, onCardPressed = {

    })
}

@Composable
fun HorizontalScrollableComponent(
    listOfBooks: List<MBook>,
    onCardPressed: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Row (
        modifier = modifier
            .fillMaxWidth()
            .heightIn(280.dp)
            .horizontalScroll(scrollState)
    ){
        for(book in listOfBooks){
            ListCard(book = book, onPressDetails = {
                onCardPressed(it)
            })
        }
    }
}
