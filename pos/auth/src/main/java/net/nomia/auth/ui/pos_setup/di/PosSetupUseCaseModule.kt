package net.nomia.auth.ui.pos_setup.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import net.nomia.auth.ui.pos_setup.domain.AuthUseCase
import net.nomia.auth.ui.pos_setup.domain.AuthUseCaseImpl
import net.nomia.auth.ui.pos_setup.domain.MenusUseCase
import net.nomia.auth.ui.pos_setup.domain.MenusUseCaseImpl
import net.nomia.auth.ui.pos_setup.domain.OrganizationsUseCase
import net.nomia.auth.ui.pos_setup.domain.OrganizationsUseCaseImpl
import net.nomia.auth.ui.pos_setup.domain.RestoreTerminalUseCase
import net.nomia.auth.ui.pos_setup.domain.RestoreTerminalUseCaseImpl
import net.nomia.auth.ui.pos_setup.domain.SaveTerminalUseCase
import net.nomia.auth.ui.pos_setup.domain.SaveTerminalUseCaseImpl
import net.nomia.auth.ui.pos_setup.domain.ServerProviderUseCase
import net.nomia.auth.ui.pos_setup.domain.ServerProviderUseCaseImpl
import net.nomia.auth.ui.pos_setup.domain.StoresUseCase
import net.nomia.auth.ui.pos_setup.domain.StoresUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface PosSetupUseCaseModule {

    @Binds
    fun bindAuthUseCase(
        authUseCase: AuthUseCaseImpl,
    ): AuthUseCase

    @Binds
    fun bindOrganizationsUseCase(
        organizationsUseCase: OrganizationsUseCaseImpl,
    ): OrganizationsUseCase

    @Binds
    fun bindStoresUseCase(
        storesUseCase: StoresUseCaseImpl,
    ): StoresUseCase

    @Binds
    fun bindMenusUseCase(
        menusUseCase: MenusUseCaseImpl,
    ): MenusUseCase

    @Binds
    fun bindSaveTerminalUseCase(
        saveTerminalUseCase: SaveTerminalUseCaseImpl,
    ): SaveTerminalUseCase

    @Binds
    fun bindRestoreTerminalUseCase(
        restoreTerminalUseCase: RestoreTerminalUseCaseImpl,
    ): RestoreTerminalUseCase

    @Binds
    fun bindServerProviderUseCase(
        serverProviderUseCase: ServerProviderUseCaseImpl,
    ): ServerProviderUseCase

}
