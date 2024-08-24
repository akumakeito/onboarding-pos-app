package net.nomia.onboarding.data.local

import android.content.SharedPreferences
import androidx.core.content.edit
import net.nomia.onboarding.R
import net.nomia.onboarding.data.local.di.UserDataSharedPrefModule.Companion.SharedPreferenceUserData
import net.nomia.onboarding.data.toEntity
import net.nomia.onboarding.domain.model.Store
import net.nomia.pos.core.data.Response
import net.nomia.pos.core.exception.NomiaException
import net.nomia.pos.core.text.Content
import java.util.UUID
import javax.inject.Inject

interface LocalDataSource {
    fun saveUserData(key: String, value: String)
    suspend fun saveStore(store: Store): Response<out Unit>
    suspend fun updateStoreIdFromRemote(storeUUID: UUID): Response<out Unit>
}

internal class LocalDataSourceImpl @Inject constructor(
    @SharedPreferenceUserData private val sharedPreferences: SharedPreferences,
    private val storeDao: StoreDao
) : LocalDataSource {
    override fun saveUserData(key: String, value: String) {
        sharedPreferences.edit {
            putString(key, value)
        }
    }

    override suspend fun saveStore(store: Store): Response<out Unit> =
        try {
            storeDao.saveStore(store.toEntity())
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error(Content.ResValue(R.string.add_local_error), e)
        }


    override suspend fun updateStoreIdFromRemote(storeUUID: UUID): Response<out Unit> =
        try {
            val lastAddedStore =
                storeDao.getLastAddedStore() ?: throw NomiaException("Store not found")
            storeDao.updateStoreId(lastAddedStore.id, storeUUID)
            Response.Success(Unit)
        } catch (e: Exception) {
            Response.Error(Content.ResValue(R.string.add_local_error), e)
        }
}