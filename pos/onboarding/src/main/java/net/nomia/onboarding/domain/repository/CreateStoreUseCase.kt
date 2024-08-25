package net.nomia.onboarding.domain.repository

import net.nomia.onboarding.domain.model.Store
import net.nomia.pos.core.data.Response
import javax.inject.Inject


interface CreateStoreUseCase {
    suspend operator fun invoke(store: Store) : Response<out Unit>
}

internal class CreateStoreUseCaseImpl @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
) : CreateStoreUseCase {

    override suspend fun invoke(store: Store) : Response< out Unit> {
        return onboardingRepository.createStore(store = store)
    }

}

