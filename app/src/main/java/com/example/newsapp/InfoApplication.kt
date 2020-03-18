package com.example.newsapp

import android.app.Application
import com.example.newsapp.data.DataRepository
import com.example.newsapp.viewmodel.InfoViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class InfoApplication:Application(),KodeinAware {
    override val kodein: Kodein=Kodein.lazy {
        import(androidXModule(this@InfoApplication))

        bind() from singleton { DataRepository() }
        bind() from singleton {
            InfoViewModelFactory(instance())
        }
    }


}