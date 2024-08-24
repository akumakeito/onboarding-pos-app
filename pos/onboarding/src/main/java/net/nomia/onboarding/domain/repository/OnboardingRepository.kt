package net.nomia.onboarding.domain.repository

import net.nomia.onboarding.domain.model.Store
import net.nomia.pos.core.data.Response

interface OnboardingRepository {

    suspend fun createStore(store : Store) : Response< out Unit>
    suspend fun saveUserData(key: String, value: String)

}