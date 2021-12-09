package com.example.codechallenge2.home

import android.app.Application
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.codechallenge2.extensions.Event
import com.example.codechallenge2.model.Card
import com.example.codechallenge2.model.DisplayCard
import com.example.codechallenge2.model.TextField
import com.example.codechallenge2.network.APIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _cards = MutableLiveData(listOf<DisplayCard>())
    val cards: LiveData<List<DisplayCard>> = _cards

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    val hideKeyboard = Event(Unit)

    init {
        val apiService = APIService.retrofit.create(APIService::class.java)
        val call = apiService.cards

        _loading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = call.execute()
            val cardsFromCall = result.body()?.page?.cards ?: return@launch

            _cards.postValue(cardsFromCall.map {
                if (it.cardType == "text") {
                    val textField = TextField()
                    textField.text = it.card?.text.orEmpty()
                    textField.attributes = it.card?.attributes
                    DisplayCard(textField)
                } else {
                    DisplayCard(it.card?.title, it.card?.description, it.card?.image)
                }
            })
            _loading.postValue(false)
            viewModelScope.launch(Dispatchers.Main) { hideKeyboard.raiseEvent(Unit) }
        }
    }
}

//@BindingAdapter("queryTextListener")
//fun setOnQueryTextListener(searchView: SearchView, listener: SearchView.OnQueryTextListener) {
//    searchView.setOnQueryTextListener(listener);
//}