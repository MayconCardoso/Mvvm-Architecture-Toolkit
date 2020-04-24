package com.mctech.architecture.mvvm

import android.app.Application
import com.mctech.architecture.mvvm.data.ImageDataSource
import com.mctech.architecture.mvvm.data.ImageMockedDataSource
import com.mctech.architecture.mvvm.data.ImageRepository
import com.mctech.architecture.mvvm.domain.interactions.LoadImageDetailsCase
import com.mctech.architecture.mvvm.domain.interactions.LoadImageListCase
import com.mctech.architecture.mvvm.domain.service.ImageService
import com.mctech.architecture.mvvm.presentation.ImageViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initDependencyInjection()
    }

    private fun initDependencyInjection() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    dataModuleDependencies,
                    useCaseDependencies,
                    viewModeDependencies
                )
            )
        }
    }
}

private val dataModuleDependencies = module {
    single {
        ImageMockedDataSource() as ImageDataSource
    }

    single {
        ImageRepository(
            dataSource = get()
        ) as ImageService
    }
}

private val useCaseDependencies = module {
    factory {
        LoadImageListCase(
            service = get()
        )
    }

    factory {
        LoadImageDetailsCase(
            service = get()
        )
    }
}

private val viewModeDependencies = module {
    viewModel {
        ImageViewModel(
            loadImageListCase = get(),
            loadImageDetailsCase = get()
        )
    }
}