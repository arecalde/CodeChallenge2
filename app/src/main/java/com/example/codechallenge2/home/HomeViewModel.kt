package com.example.codechallenge2.home

import android.app.Application
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.codechallenge2.extensions.Event
import com.example.codechallenge2.model.*
import com.example.codechallenge2.network.APIService
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    var cards = mutableStateListOf<DisplayCard>()
    var isRefreshing by mutableStateOf(false)

    val hideKeyboard = Event(Unit)
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

        val result = call.execute()
        val cardsFromCall = result.body()?.page?.cards ?: return@withContext emptyList()

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

@ExperimentalUnitApi
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
                    ComposeCard(item)
                }
            }
        }
    }
}

//@BindingAdapter("queryTextListener")
//fun setOnQueryTextListener(searchView: SearchView, listener: SearchView.OnQueryTextListener) {
//    searchView.setOnQueryTextListener(listener);
//}