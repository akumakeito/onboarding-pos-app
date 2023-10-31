package net.nomia.pos.ui.auth.pos_setup.domain

import kotlinx.coroutines.flow.Flow
import net.nomia.settings.domain.SettingsRepository
import net.nomia.settings.domain.model.ServerProvider
import javax.inject.Inject

interface ServerProviderUseCase {

    fun get(): Flow<ServerProvider>

}

class ServerProviderUseCaseImpl @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ServerProviderUseCase {

    override fun get(): Flow<ServerProvider> = settingsRepository.getServerProvider()

}
