package com.cursosant.winesbase.updateModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursosant.winesbase.commonModule.entities.Wine
import com.cursosant.winesbase.updateModule.intent.UpdateIntent
import com.cursosant.winesbase.updateModule.model.UpdateRepository
import com.cursosant.winesbase.updateModule.model.UpdateState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateViewModel(private val repository: UpdateRepository) : ViewModel() {

    private val _state = MutableStateFlow<UpdateState>(UpdateState.Init)
    val state: StateFlow<UpdateState> = _state

    val channel = Channel<UpdateIntent>(Channel.UNLIMITED)


    init {
        setupIntent()
    }

    private fun setupIntent() {
        viewModelScope.launch {
            channel.consumeAsFlow().collect { intent ->
                when(intent){
                    is UpdateIntent.UpdateWine-> updateWines(intent.wine)
                    is UpdateIntent.RequestWine -> requestWine(intent.id)

                }
            }
        }
    }

    private suspend fun requestWine(id: Double) {
        _state.value = UpdateState.ShowProgress
        try {
            withContext(Dispatchers.IO){
                _state.value = repository.requestWine(id)
            }
        }finally{
            _state.value = UpdateState.HideProgress
        }


    }

    private suspend fun updateWines(wine: Wine) {
        _state.value = UpdateState.ShowProgress
        try {
            withContext(Dispatchers.IO){
                _state.value = repository.updateWine(wine)
            }
        }finally {
            _state.value = UpdateState.HideProgress
        }


    }
}