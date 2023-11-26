package net.nomia.settings.domain

import kotlinx.coroutines.flow.Flow
import net.nomia.settings.data.local.entity.AuthTokenData
import net.nomia.settings.domain.model.ServerProvider

interface SettingsRepository {


    fun getServerProvider(): Flow<ServerProvider>
    suspend fun saveServerProvider(serverProvider: ServerProvider)

    fun getAuthToken(): Flow<AuthTokenData?>
    suspend fun saveAuthToken(applicationToken: AuthTokenData)

    suspend fun deleteAll()

}
