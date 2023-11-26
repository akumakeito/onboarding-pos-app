package net.nomia.auth.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.nomia.settings.config.SettingsDatabase

interface LogoutUseCase {
    suspend fun invoke()
}

class LogoutUseCaseImpl(
    private val settingsDatabase: SettingsDatabase,
) : LogoutUseCase {

    override suspend fun invoke() {
        withContext(Dispatchers.IO) {
            settingsDatabase.settingsDao().deleteAll()
        }
    }
}
