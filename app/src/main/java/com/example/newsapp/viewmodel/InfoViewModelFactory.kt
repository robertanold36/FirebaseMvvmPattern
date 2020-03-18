package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.data.DataRepository
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class InfoViewModelFactory(private val repository: DataRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(InfoViewModel::class.java)) {
            return InfoViewModel(repository) as T
        }

        throw IllegalArgumentException("unknown view model")
    }
}