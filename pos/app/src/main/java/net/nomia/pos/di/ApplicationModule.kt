package net.nomia.pos.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import net.nomia.auth.domain.LogoutUseCase
import net.nomia.auth.domain.LogoutUseCaseImpl
import net.nomia.pos.core.handler.OnApplicationCreated
import net.nomia.settings.config.SettingsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ApplicationModule {

    @Singleton
    @Provides
    fun applicationScopeProvide(): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Main)

    @Provides
    fun provideLogoutUseCase(
        settingsDatabase: SettingsDatabase,
    ): LogoutUseCase = LogoutUseCaseImpl(
        settingsDatabase = settingsDatabase,
    )

    @Singleton
    @Provides
    @IntoSet
    fun provideDummyOnApplicationCreated() = OnApplicationCreated {}

}
