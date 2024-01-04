package net.nomia.main.domain

import com.auth0.android.jwt.JWT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.nomia.main.domain.model.Auth
import net.nomia.main.domain.model.Principal
import net.nomia.settings.data.local.entity.AuthTokenData
import net.nomia.settings.domain.SettingsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrincipalRepository @Inject constructor(
    private val scope: CoroutineScope,
    private val erpLoginRepository: ErpLoginRepository,
    private val settingsRepository: SettingsRepository,
) {

    private val principal = MutableStateFlow<Principal?>(null)
    val currentPrincipal = principal.asStateFlow()

    init {
        settingsRepository.getAuthToken()
            .map { authData ->
                if (authData != null) {
                    val auth = Auth(JWT(authData.accessToken), JWT(authData.refreshToken))
                    val authFlow = erpLoginRepository.watchRefreshToken(auth)
                        .shareIn(scope, SharingStarted.Eagerly, 1)
                    principal.update { Principal(authFlow) }
                } else {
                    principal.update { null }
                }

            }
            .stateIn(scope, SharingStarted.Eagerly, null)
    }


    fun login(auth: Auth) {
        scope.launch {
            settingsRepository.saveAuthToken(
                AuthTokenData(auth.accessToken.toString(), auth.refreshToken.toString())
            )
        }
    }

}
