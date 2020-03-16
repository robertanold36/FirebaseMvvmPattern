package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.Data.DataRepository
import com.example.newsapp.Data.MyData

class InfoViewModel(private val repository: DataRepository):ViewModel() {

    fun getAllInformation() : LiveData<MutableList<MyData>>{
        val mutableData=MutableLiveData<MutableList<MyData>>()
        repository.getAllInfo().observeForever{
            mutableData.value=it
        }
      return mutableData
    }

}