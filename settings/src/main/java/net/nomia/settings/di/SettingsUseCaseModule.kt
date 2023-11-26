package net.nomia.settings.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.nomia.settings.domain.usecase.ObserveServerProviderUseCase
import net.nomia.settings.domain.usecase.ObserveServerProviderUseCaseImpl
import net.nomia.settings.domain.usecase.SetServerProviderUseCase
import net.nomia.settings.domain.usecase.SetServerProviderUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface SettingsUseCaseModule {

    @Binds
    fun bindSetServerProviderUseCase(
        setServerProviderUseCase: SetServerProviderUseCaseImpl
    ): SetServerProviderUseCase

    @Binds
    fun bindObserveServerProviderUseCase(
        getServerProviderUseCase: ObserveServerProviderUseCaseImpl
    ): ObserveServerProviderUseCase

}
