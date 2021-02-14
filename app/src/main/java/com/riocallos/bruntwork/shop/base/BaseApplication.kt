package com.riocallos.bruntwork.shop.base

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.multidex.MultiDex
import androidx.room.Room
import com.riocallos.bruntwork.shop.binding.BaseDataBindingComponent
import com.riocallos.bruntwork.shop.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.HasServiceInjector
import javax.inject.Inject

class BaseApplication : Application(), HasActivityInjector, HasServiceInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = dispatchingAndroidInjector

    @Inject
    lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<Service>

    override fun serviceInjector() = dispatchingServiceInjector

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
        DataBindingUtil.setDefaultComponent(BaseDataBindingComponent())
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

}