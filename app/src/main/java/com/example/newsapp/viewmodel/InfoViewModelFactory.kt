package com.example.newsapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.Data.DataRepository

class InfoViewModelFactory(private val repository: DataRepository):ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return InfoViewModel(repository) as T
    }
}