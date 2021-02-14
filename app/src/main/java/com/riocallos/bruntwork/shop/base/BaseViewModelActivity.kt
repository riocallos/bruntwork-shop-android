package com.riocallos.bruntwork.shop.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.riocallos.bruntwork.shop.di.ViewModelFactory
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

/**
 * Automatically initializes ViewDataBinding class and ViewModel class for your activity.
 */
abstract class BaseViewModelActivity<B : ViewDataBinding, VM : BaseViewModel> : BaseActivity<B>() {

    @Inject
    lateinit var factory: ViewModelFactory

    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelClass = (javaClass.genericSuperclass as ParameterizedType)
                .actualTypeArguments[1] as Class<VM>

        viewModel = ViewModelProvider(this, factory)
                .get(viewModelClass)
        viewModel.onCreate(intent.extras)
    }

}