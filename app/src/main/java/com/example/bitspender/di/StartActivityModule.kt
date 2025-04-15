package com.example.bitspender.di

import com.example.bitspender.di.scope.ActivityScope
import com.example.bitspender.presentation.StartActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class StartActivityModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            FragmentBuildersModule::class
        ]
    )


    abstract fun createStartActivityInjector(): StartActivity

}