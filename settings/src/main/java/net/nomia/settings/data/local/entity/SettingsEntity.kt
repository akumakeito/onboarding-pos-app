package net.nomia.settings.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.nomia.settings.data.toEntity
import net.nomia.settings.domain.model.ServerProvider
import java.util.UUID

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    @Embedded(prefix = "server_provider_")
    var serverProvider: ServerProviderData = ServerProvider.DEFAULT.toEntity(),
    @Embedded(prefix = "auth_token_")
    var accessToken: AuthTokenData? = null,
)
