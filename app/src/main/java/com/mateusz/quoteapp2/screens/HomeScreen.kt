package com.mateusz.quoteapp2.screens

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mateusz.quoteapp2.data.MainViewModel
import com.mateusz.quoteapp2.data.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(mainVm: MainViewModel, context: Context) {

    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val savedPage = sharedPreferences.getInt("currentPage", 0)

    val pagerState = rememberPagerState(initialPage = savedPage)
    Box(modifier = Modifier.fillMaxWidth()){
    HorizontalPager(pageCount = 404, state = pagerState) { page ->
        val quoteState = remember { mutableStateOf("") }
        val authorState = remember { mutableStateOf("") }
        val sourceState = remember { mutableStateOf("") }
        val numberState = remember { mutableStateOf(page + 1) }

        LaunchedEffect(Unit) {
            val quote = withContext(Dispatchers.IO) {
                mainVm.getQuoteById(numberState.value)
            }
            if (quote != null) {
                quoteState.value = quote.quote
                authorState.value = quote.author
                sourceState.value = quote.source
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 110.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())

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
        Box(modifier = Modifier.fillMaxSize()) {
            val coroutineScope = rememberCoroutineScope()

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(64.dp)
            ) {
                IconButton(
                    modifier = Modifier.size(32.dp),
                    onClick = {
                        coroutineScope.launch {
                            pagerState.scrollToPage(0)
                        }
                    },
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset quote"
                    )
                }
                Spacer(modifier = Modifier.width(32.dp))
                IconButton(
                    modifier = Modifier.size(32.dp),
                    onClick = {
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "${quoteState.value}\n\n- ${authorState.value}")
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)
                    },
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share"
                    )
                }

                Spacer(modifier = Modifier.width(32.dp))
                IconButton(
                    modifier = Modifier.size(32.dp),
                    onClick = {
                        coroutineScope.launch {
                            val quote = Quote(quoteState.value,authorState.value,sourceState.value,0)
                            if (quote != null) {
                                mainVm.addToFavourites(quote)
                                Log.d("Tag", "powinno dodać")
                            }

                        }
                    },
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Add to favorites"
                    )
                }
            }

        }


        DisposableEffect(Unit) {
            onDispose {
                val editor = sharedPreferences.edit()
                editor.putInt("currentPage", pagerState.currentPage)
                editor.apply()
            }
        }
    }
    Text(
        text = "${pagerState.currentPage + 1} of 404",
        fontSize = 16.sp,
        textAlign = TextAlign.End,
        style = MaterialTheme.typography.body2,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .align(Alignment.BottomEnd)
    )
    }
}

