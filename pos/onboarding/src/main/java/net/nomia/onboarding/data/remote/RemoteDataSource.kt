package net.nomia.onboarding.data.remote

import com.auth0.android.jwt.JWT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import net.nomia.common.data.model.Organization
import net.nomia.erp.api.ErpClientService
import net.nomia.erp.apiFlow
import net.nomia.erp.mutation.CreateStoreMutation
import net.nomia.erp.query.GetAllOrganizationsQuery
import net.nomia.erp.query.GetCatalogQuery
import net.nomia.erp.schema.type.PageRequestInput
import net.nomia.main.domain.PrincipalRepository
import net.nomia.main.domain.toDomain
import net.nomia.onboarding.R
import net.nomia.onboarding.data.toStoreCreateInput
import net.nomia.onboarding.data.toStoreResponse
import net.nomia.onboarding.domain.model.Store
import net.nomia.pos.core.data.Response
import net.nomia.pos.core.exception.NomiaException
import net.nomia.pos.core.text.Content
import java.util.UUID
import javax.inject.Inject

interface RemoteDataSource {
    suspend fun createStore(store: Store) :  Flow<Response<out StoreResponse>>
}

internal class RemoteDataSourceImpl @Inject constructor(
    private val erpClientService: ErpClientService,
    private val principalRepository: PrincipalRepository,
) : RemoteDataSource {

    private val authState = principalRepository.currentPrincipal.value?.auth


    override suspend fun createStore(store: Store) : Flow<Response<out StoreResponse>> {
        val token =
            authState?.first()?.accessToken ?: throw NomiaException("Access token not found")

        val organizationId = getOrganization(token)
        val catalogId = getCatalog(token, organizationId)

        return erpClientService.principalClient(token = token, organizationId = organizationId)
            .take(1)
            .flatMapLatest { client ->
                client.mutate(
                    CreateStoreMutation(
                        store.toStoreCreateInput(
                            organizationId.value,
                            catalogId
                        )
                    )
                )
                    .toBuilder()
                    .build()
                    .apiFlow()
            }.transform {
                it.data?.createStoreV2?.output?.toStoreResponse()?.let { storeResponse ->
                    emit(Response.Success(storeResponse))
                }
                    ?: emit(Response.Error(message = Content.ResValue(R.string.unknown_error)))

            }.flowOn(Dispatchers.IO)
    }


    private suspend fun getOrganization(token: JWT): Organization.ID {
        val organizations = erpClientService.principalClient(token = token)
            .take(1)
            .flatMapLatest { client ->
                client.query(GetAllOrganizationsQuery(PageRequestInput(0, 10)))
                    .toBuilder()
                    .build()
                    .apiFlow()
            }.first()
        return organizations.data?.allOrganizationsPageable?.content?.first()
            ?.fragments?.organizationFragment?.toDomain()?.id
            ?: throw NomiaException("Organization not found")
    }


    private suspend fun getCatalog(
        token: JWT,
        organizationId: Organization.ID
    ): UUID {

        val catalog = erpClientService.principalClient(
            token = token,
            organizationId = organizationId
        ).take(1)
            .flatMapLatest { client ->
                client.query(GetCatalogQuery())
                    .toBuilder()
                    .build()
                    .apiFlow()
            }.first()
        return catalog.data?.catalogByOrganization?.id ?: throw NomiaException("Catalog not found")
    }
}