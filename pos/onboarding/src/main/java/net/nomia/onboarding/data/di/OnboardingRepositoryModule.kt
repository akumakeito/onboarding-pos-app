package net.nomia.onboarding.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.nomia.onboarding.data.OnboardingRepositoryImpl
import net.nomia.onboarding.data.local.LocalDataSource
import net.nomia.onboarding.data.local.LocalDataSourceImpl
import net.nomia.onboarding.data.remote.RemoteDataSource
import net.nomia.onboarding.data.remote.RemoteDataSourceImpl
import net.nomia.onboarding.domain.repository.CreateStoreUseCase
import net.nomia.onboarding.domain.repository.CreateStoreUseCaseImpl
import net.nomia.onboarding.domain.repository.OnboardingRepository
import net.nomia.onboarding.domain.repository.SaveUserDataUseCase
import net.nomia.onboarding.domain.repository.SaveUserDataUseCaseImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal interface OnboardingRepositoryModule {

    @Binds
    @Singleton
    fun bindOnboardingRepository(onboardingRepositoryImpl: OnboardingRepositoryImpl): OnboardingRepository

    @Binds
    @Singleton
    fun bindCreateStoreUseCase(createStoreUseCaseImpl: CreateStoreUseCaseImpl): CreateStoreUseCase

    @Binds
    @Singleton
    fun bindSaveUserDataUseCase(saveUserDataUseCaseImpl: SaveUserDataUseCaseImpl): SaveUserDataUseCase

    @Binds
    @Singleton
    fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    @Singleton
    fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource
}