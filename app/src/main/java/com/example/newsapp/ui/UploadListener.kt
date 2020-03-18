package com.example.newsapp.ui


interface UploadListener {

    fun onStarted()
    fun onSuccess()
    fun onFailure(message: String)
}