package com.example.bitspender.presentation

import android.app.Application
import com.example.bitspender.di.AppComponent
import com.example.bitspender.di.DaggerAppComponent
import com.example.bitspender.di.utils.AppInitializers
import com.example.bitspender.di.utils.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class BitSpenderApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var appInitializers: AppInitializers

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .appContext(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this) { appComponent }

        appInitializers.init(this)

    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}