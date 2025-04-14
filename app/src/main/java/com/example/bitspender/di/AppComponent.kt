package com.example.bitspender.di

import android.app.Application
import android.content.Context
import com.example.bitspender.presentation.BitSpenderApp
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        NetworkModule::class,
        DatabaseModule::class,
        DatabaseModule::class,
        RepositoryModule::class
    ]
)

interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun appContext(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(app: BitSpenderApp)

}