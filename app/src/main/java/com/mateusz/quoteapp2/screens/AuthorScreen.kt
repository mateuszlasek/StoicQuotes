package com.mateusz.quoteapp2.screens

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mateusz.quoteapp2.viewmodel.MainViewModel
import com.mateusz.quoteapp2.data.model.Quote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



private val showQuoteScreen = mutableStateOf(false)
private val dataLoaded = mutableStateOf(false)

@Composable
fun AuthorScreen(mainVm: MainViewModel, context: Context) {
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val quoteIdsState = remember { mutableStateOf(emptyList<Int>()) }
    val authorState = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()
    //val dataLoaded = remember { mutableStateOf(false) }
    val themeColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, themeColor, RoundedCornerShape(16.dp))
                .clickable(onClick = {

                    val author = "Marcus Aurelius" // przykładowy autor wybrany przez użytkownika
                    authorState.value = author
                    showQuoteScreen.value = true
                    val editor = sharedPreferences.edit()
                    editor.putInt("author", 1)
                    editor.apply()

                }),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Marcus Aurelius",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, themeColor, RoundedCornerShape(16.dp))
                .clickable(onClick = {
                    val author = "Epictetus" // przykładowy autor wybrany przez użytkownika
                    authorState.value = author
                    showQuoteScreen.value = true
                    val editor = sharedPreferences.edit()
                    editor.putInt("author", 2)
                    editor.apply()
                }),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Epictetus",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(2.dp, themeColor, RoundedCornerShape(16.dp))
                .clickable(onClick = {
                    val author = "Seneca" // przykładowy autor wybrany przez użytkownika
                    authorState.value = author
                    showQuoteScreen.value = true
                    val editor = sharedPreferences.edit()
                    editor.putInt("author", 3)
                    editor.apply()
                }),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Seneca",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }

    if(showQuoteScreen.value) {
        LaunchedEffect(Unit){
            val quoteIds = mainVm.getAllIdsByAuthor(authorState.value)


            if (quoteIds != null) {
                withContext(Dispatchers.Main) {
                    quoteIdsState.value = quoteIds

                }
            }

            dataLoaded.value=true
        }
        if(dataLoaded.value) {
            AuthorQuoteScreen(quoteIdsState.value, mainVm, context)

        }
        DisposableEffect(Unit) {
            onDispose {
                //dataLoaded.value=false
                showQuoteScreen.value=false
                quoteIdsState.value=emptyList()
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuthorQuoteScreen(quoteIds: List<Int>, mainVm: MainViewModel, context:Context){
    val themeColor = if (!isSystemInDarkTheme()) Color.White else Color.Black
    val sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val author = sharedPreferences.getInt("author", 0)
    val savedPage = when(author){
        1 -> sharedPreferences.getInt("marcus_aurelius",0)
        2 -> sharedPreferences.getInt("epictetus",0)
        3 -> sharedPreferences.getInt("seneca",0)
        else -> 0
    }


    val pagerState = rememberPagerState(initialPage = savedPage)
    val coroutineScope = rememberCoroutineScope()

    HorizontalPager(pageCount = quoteIds.size ,state = pagerState) { page ->
        val quoteState = remember { mutableStateOf("") }
        val authorState = remember { mutableStateOf("") }
        val sourceState = remember { mutableStateOf("") }
        val numberState = remember { mutableStateOf(page+1) }
        LaunchedEffect(Unit) {
                if (page < quoteIds.size) {
                    val quote = mainVm.getQuoteById(quoteIds[page])

                    if (quote != null) {
                        withContext(Dispatchers.Main) {
                            quoteState.value = quote.quote
                            authorState.value = quote.author
                            sourceState.value = quote.source
                        }
                    }

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
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
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
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "${quoteState.value}\n\n- ${authorState.value}"
                            )
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
                            val quote =
                                Quote(quoteState.value, authorState.value, sourceState.value, 0)
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
    }

    DisposableEffect(Unit) {
        onDispose {
            val editor = sharedPreferences.edit()
            when(author) {
                1 -> editor.putInt("marcus_aurelius", pagerState.currentPage)
                2 -> editor.putInt("epictetus",pagerState.currentPage)
                3 -> editor.putInt("seneca", pagerState.currentPage)
            }
            editor.apply()
            dataLoaded.value=false
        }
    }
}

