package com.riocallos.bruntwork.shop.di

import android.app.Application
import com.riocallos.bruntwork.shop.base.BaseApplication
import com.riocallos.bruntwork.shop.di.builders.ActivityBuilder
import com.riocallos.bruntwork.shop.di.modules.*
import com.riocallos.bruntwork.shop.di.providers.AssetProvider
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AppModule::class,
            AndroidSupportInjectionModule::class,
            ActivityBuilder::class,
            ViewModelModule::class,
            SchedulerModule::class,
            RepositoryModule::class,
            DatabaseModule::class,
            RemoteModule::class
        ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent
    }

    fun inject(app: BaseApplication)
}