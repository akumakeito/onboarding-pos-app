package net.nomia.onboarding.domain.repository

import javax.inject.Inject

interface SaveUserDataUseCase {
    suspend operator fun invoke(key: String, value: String)
}

internal class SaveUserDataUseCaseImpl @Inject constructor(
    private val onboardingRepository: OnboardingRepository,
) : SaveUserDataUseCase {

    override suspend fun invoke(key: String, value: String) {
        onboardingRepository.saveUserData(key, value)
    }
}