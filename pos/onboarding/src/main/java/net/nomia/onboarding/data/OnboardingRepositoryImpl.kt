package net.nomia.onboarding.data

import kotlinx.coroutines.flow.last
import net.nomia.onboarding.data.local.LocalDataSource
import net.nomia.onboarding.data.remote.RemoteDataSource
import net.nomia.onboarding.domain.model.Store
import net.nomia.onboarding.domain.repository.OnboardingRepository
import net.nomia.pos.core.data.Response
import javax.inject.Inject

internal class OnboardingRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : OnboardingRepository {

    override suspend fun createStore(store: Store): Response<out Unit> {
        val localResult = localDataSource.saveStore(store)

        if (localResult is Response.Error) {
            Response.Error(localResult.message, localResult.throwable)
        }
        val remoteResult = remoteDataSource.createStore(store)

        val response = remoteResult.last()
        return when (response) {
            is Response.Error -> {
                Response.Error(response.message, response.throwable)
            }

            is Response.Success -> {
                val updateIdResult = localDataSource.updateStoreIdFromRemote(response.result.id)
                if (updateIdResult is Response.Error) {
                    Response.Error(updateIdResult.message, updateIdResult.throwable)
                }
                Response.Success(Unit)
            }

            else -> {
                Response.Timeout
            }
        }
    }

    override suspend fun saveUserData(key: String, value: String) {
        localDataSource.saveUserData(key, value)
    }

}

