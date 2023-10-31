package net.nomia.pos.ui.auth.pos_setup

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import net.nomia.pos.ui.auth.pos_setup.domain.MenusUseCase
import net.nomia.pos.ui.auth.pos_setup.domain.OrganizationsUseCase
import net.nomia.pos.ui.auth.pos_setup.domain.RestoreTerminalUseCase
import net.nomia.pos.ui.auth.pos_setup.domain.SaveTerminalUseCase
import net.nomia.pos.ui.auth.pos_setup.domain.ServerProviderUseCase
import net.nomia.pos.ui.auth.pos_setup.domain.StoresUseCase
import net.nomia.pos.ui.auth.pos_setup.mvi.PosSetupMviActor
import net.nomia.pos.ui.auth.pos_setup.mvi.PosSetupMviBootstrap
import net.nomia.pos.ui.auth.pos_setup.mvi.PosSetupMviFeatureFactory
import net.nomia.pos.ui.domain.LogoutUseCase


@Module
@InstallIn(ViewModelComponent::class)
@Suppress("TooManyFunctions")
class PosSetupModule {

    @Provides
    internal fun providePosSetupMviBootstrap(
        organizationsUseCase: OrganizationsUseCase,
        serverProviderUseCase: ServerProviderUseCase,
    ): PosSetupMviBootstrap = PosSetupMviBootstrap(organizationsUseCase, serverProviderUseCase)

    @Provides
    internal fun provideMenuMviActor(
        organizationsUseCase: OrganizationsUseCase,
        storesUseCase: StoresUseCase,
        menuItemUseCase: MenusUseCase,
        saveTerminalUseCase: SaveTerminalUseCase,
        restoreTerminalUseCase: RestoreTerminalUseCase,
        logoutUseCase: LogoutUseCase,
    ): PosSetupMviActor = PosSetupMviActor(
        organizationsUseCase, storesUseCase, menuItemUseCase, saveTerminalUseCase,
        restoreTerminalUseCase, logoutUseCase,
    )

    @Provides
    internal fun provideFeatureFactory(
        bootstrap: PosSetupMviBootstrap,
        actor: PosSetupMviActor
    ): PosSetupMviFeatureFactory = PosSetupMviFeatureFactory(
        bootstrap = bootstrap,
        actor = actor
    )
}
