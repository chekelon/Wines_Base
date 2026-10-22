package com.cursosant.winesbase.updateModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cursosant.winesbase.updateModule.model.UpdateRepository

class UpdateViewModelFactory(val repository : UpdateRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UpdateViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return UpdateViewModel(repository) as T
        }
        throw IllegalArgumentException("Clase de ViewModel desconocida")
    }
}