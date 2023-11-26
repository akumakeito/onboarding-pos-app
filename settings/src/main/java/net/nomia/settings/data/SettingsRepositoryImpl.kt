package net.nomia.settings.data

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import net.nomia.settings.config.SettingsDatabase
import net.nomia.settings.data.local.entity.AuthTokenData
import net.nomia.settings.data.local.entity.SettingsEntity
import net.nomia.settings.domain.SettingsRepository
import net.nomia.settings.domain.model.ServerProvider
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDatabase: SettingsDatabase
) : SettingsRepository {

    private val dao = settingsDatabase.settingsDao()


    override fun getServerProvider(): Flow<ServerProvider> {
        return getSettings().mapLatest { it.serverProvider }
            .distinctUntilChanged()
            .map { it.toDomain() }
    }

    override suspend fun saveServerProvider(serverProvider: ServerProvider) {
        updateSettings { currentSettings ->
            dao.save(currentSettings.copy(serverProvider = serverProvider.toEntity()))
        }
    }

    override fun getAuthToken(): Flow<AuthTokenData?> {
        return getSettings().map { it.accessToken }
            .distinctUntilChanged()
    }

    override suspend fun saveAuthToken(auth: AuthTokenData) {
        updateSettings { currentSettings ->
            dao.save(currentSettings.copy(accessToken = auth))
        }
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    private suspend inline fun updateSettings(crossinline block: suspend (SettingsEntity) -> Unit) {
        settingsDatabase.withTransaction {
            val oldSettings = getSettings().first()
            block(oldSettings)
        }
    }

    private fun getSettings(): Flow<SettingsEntity> =
        dao.findFirst()
            .map { it ?: SettingsEntity() }
            .distinctUntilChanged()
}
