package com.reza.sampah.di

import com.example.core.domain.usecase.TrashInteractor
import com.example.core.domain.usecase.TrashUsecase
import com.example.core.domain.usecase.UserInteractor
import com.example.core.domain.usecase.UserUsecase
import com.reza.sampah.ui.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val usecaseModule = module {
    single<UserUsecase> { UserInteractor(get()) }
    single<TrashUsecase> { TrashInteractor(get(), get()) }
}
val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { AdminViewModel(get()) }
    viewModel { JemputViewModel(get()) }
    viewModel { RiwayatViewModel(get()) }
    viewModel { RiwayatAdminViewModel(get()) }

}