package com.riocallos.bruntwork.shop.di.modules

import com.riocallos.bruntwork.shop.base.BaseSchedulerProvider
import com.riocallos.bruntwork.shop.di.providers.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SchedulerModule {
    @Provides
    @Singleton
    fun providesScheduler(): BaseSchedulerProvider = SchedulerProvider.getInstance()
}