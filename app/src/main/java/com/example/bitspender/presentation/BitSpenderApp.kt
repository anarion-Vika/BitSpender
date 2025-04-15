package com.example.bitspender.presentation

import android.app.Application
import com.example.bitspender.di.DaggerAppComponent
import com.example.bitspender.di.utils.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class BitSpenderApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this) {
            DaggerAppComponent
                .builder()
                .application(this)
                .appContext(this)
                .build()
                .inject(this)
        }

    }

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector
}