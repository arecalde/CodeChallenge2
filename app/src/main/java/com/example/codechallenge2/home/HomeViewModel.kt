package com.example.codechallenge2.home

import android.app.Application
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.codechallenge2.model.*
import com.example.codechallenge2.network.APIService
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    var cards = mutableStateListOf<DisplayCard>()
    var isRefreshing by mutableStateOf(false)

    init {
        refresh()
    }

    fun refresh() {
        isRefreshing = true
        viewModelScope.launch {
            val networkList = callNetwork()
            cards.addAll(0, networkList)
            isRefreshing = false
        }
    }



    private suspend fun callNetwork(): List<DisplayCard> = withContext(Dispatchers.IO) {
        val apiService = APIService.retrofit.create(APIService::class.java)
        val call = apiService.cards

        val result = call?.execute()
        val cardsFromCall = result?.body()?.page?.cards ?: return@withContext emptyList()

        return@withContext (cardsFromCall.map {
            if (it.cardType == "text") {
                val textField = TextField()
                textField.text = it.card?.text.orEmpty()
                textField.attributes = it.card?.attributes
                DisplayCard(textField)
            } else {
                DisplayCard(it.card?.title, it.card?.description, it.card?.image)
            }
        })
    }
}

@Composable
fun TestView() {
     val viewModel: HomeViewModel = viewModel()
    SwipeRefresh(
        state = rememberSwipeRefreshState(viewModel.isRefreshing),
        onRefresh = {
            viewModel.refresh()
        },
    ) {
        Row {
            LazyColumn {
                itemsIndexed(viewModel.cards) { i, item ->
                    if (item.image != null)
                        ImageCard(item)
                    else
                        TextCard(item)
                }
            }
        }
    }
}