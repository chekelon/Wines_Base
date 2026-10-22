package com.cursosant.winesbase.favoriteModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursosant.winesbase.commonModule.entities.Wine
import com.cursosant.winesbase.favoriteModule.intent.FavouriteIntent
import com.cursosant.winesbase.favoriteModule.model.FavouriteRepository
import com.cursosant.winesbase.favoriteModule.model.FavouriteState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouriteViewModel(private val repository: FavouriteRepository) : ViewModel() {

    private val _state = MutableStateFlow<FavouriteState>(FavouriteState.Init)
    val state: StateFlow<FavouriteState> = _state

    val channel = Channel< FavouriteIntent>(Channel.UNLIMITED)


    init {
        setupIntent()
    }

    private fun setupIntent() {
        viewModelScope.launch {
            channel.consumeAsFlow().collect { intent ->
                when(intent){
                    is FavouriteIntent.RequestWines-> getAllWines()
                    is FavouriteIntent.AddWine -> addWine(intent.wine)
                    is FavouriteIntent.DeleteWine ->  deleteWine(intent.wine)
                }
            }
        }
    }

    private suspend  fun getAllWines() {
        _state.value = FavouriteState.ShowProgress

        try {
            withContext(Dispatchers.IO){
                _state.value = repository.getAlllWines()
            }
        } finally {
            _state.value = FavouriteState.HideProgress
        }
    }

    private suspend fun addWine(wine: Wine) {
        _state.value = FavouriteState.ShowProgress

        try {
            withContext(Dispatchers.IO) {
                _state.value = repository.addWine(wine)
            }
        } finally {
            _state.value = FavouriteState.HideProgress
        }
    }

    private suspend  fun deleteWine(wine: Wine) {
        _state.value = FavouriteState.ShowProgress

        try {
            withContext(Dispatchers.IO) {
                _state.value = repository.deleteWine(wine)
            }
        } finally {
            _state.value = FavouriteState.HideProgress
        }
    }
}