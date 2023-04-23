package com.mateusz.quoteapp2.screens

import android.graphics.Paint.Align
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mateusz.quoteapp2.data.Favourite
import com.mateusz.quoteapp2.data.MainViewModel
import com.mateusz.quoteapp2.data.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val showQuoteScreen = mutableStateOf(false)

@Composable
fun FavouriteScreen(mainVm: MainViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val favourites = mainVm.getFavorites().collectAsState(initial = emptyList())
    val favouriteId = remember { mutableStateOf(0) }
    LazyColumn(
        contentPadding = PaddingValues(all = 8.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp)
    ){
        items(favourites.value.size){ favourite ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showQuoteScreen.value = true
                        favouriteId.value = favourite

                    }

            ) {
                Text(
                    text = favourites.value[favourite].quote,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                    fontSize = 24.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = favourites.value[favourite].author,
                        modifier = Modifier
                            .padding(start = 16.dp),
                        fontSize = 20.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            modifier = Modifier.padding(end=16.dp),

                            onClick = {
                                coroutineScope.launch {
                                    val quote = Quote(
                                        favourites.value[favourite].quote,
                                        favourites.value[favourite].author,
                                        favourites.value[favourite].source,
                                        favourites.value[favourite].id
                                    )
                                    if (quote != null) {
                                        mainVm.removeFromFavourites(quote)
                                    }
                                }
                            },
                        ) {
                            Icon(
                                modifier = Modifier.size(22.dp),
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Remove from favorites"
                            )
                        }

                        IconButton(
                            modifier = Modifier.padding(end=24.dp),
                            onClick = { /* Obsługa kliknięcia przycisku Share */ },
                        ) {
                            Icon(
                                modifier = Modifier.size(22.dp),
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share quote"
                            )
                        }
                    }
                }



            }
        }
    }
    if(showQuoteScreen.value){
        FavouriteQuoteScreen(mainVm,favourites.value,favouriteId.value)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouriteQuoteScreen(mainVm: MainViewModel,favourites: List<Favourite>,favouriteId: Int){
    val themeColor = if (isSystemInDarkTheme()) Color.Black else Color.White
    val pagerState = rememberPagerState(initialPage = favouriteId)
    HorizontalPager(pageCount = favourites.size, state = pagerState) { page ->
        val quoteState = remember { mutableStateOf("") }
        val authorState = remember { mutableStateOf("") }
        val sourceState = remember { mutableStateOf("") }
        val numberState = remember { mutableStateOf(page) }

        LaunchedEffect(Unit) {
            val quote = withContext(Dispatchers.IO) {
                mainVm.getFavouriteById(favourites[page].id)
            }
            if (quote != null) {
                quoteState.value = quote.quote
                authorState.value = quote.author
                sourceState.value = quote.source
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(themeColor)
        ) {
            Text(
                text = quoteState.value,
                fontSize = 30.sp,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(16.dp)
            )
            Text(

                text = authorState.value,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(16.dp),

                )

        }
    }
    DisposableEffect(Unit) {
        onDispose {
            showQuoteScreen.value=false
        }
    }
}