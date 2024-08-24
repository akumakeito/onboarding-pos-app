package net.nomia.onboarding.data

import com.apollographql.apollo.api.Input
import net.nomia.common.data.model.Organization
import net.nomia.erp.mutation.CreateStoreMutation
import net.nomia.erp.schema.type.AreaMetricsInput
import net.nomia.erp.schema.type.Currency
import net.nomia.erp.schema.type.StoreCharacteristicsInput
import net.nomia.erp.schema.type.StoreCreateInput
import net.nomia.erp.schema.type.StoreSurveyInput
import net.nomia.erp.schema.type.StoreSurveyStoreService
import net.nomia.erp.schema.type.StoreSurveyStoreType
import net.nomia.onboarding.data.local.entity.AreaMetricsData
import net.nomia.onboarding.data.local.entity.ServiceTypeData
import net.nomia.onboarding.data.local.entity.StoreData
import net.nomia.onboarding.data.local.entity.StoreTypeData
import net.nomia.onboarding.data.remote.StoreResponse
import net.nomia.onboarding.domain.model.AreaMetrics
import net.nomia.onboarding.domain.model.ServiceType
import net.nomia.onboarding.domain.model.Store
import net.nomia.onboarding.domain.model.StoreType
import net.nomia.settings.data.local.entity.OrganizationData
import java.util.UUID

fun Organization.toEntity() = OrganizationData(
    id = id.value,
    name = name,
    code = code.value,
    currency = currency,
)

fun AreaMetrics.toEntity() = AreaMetricsData(
    kitchenArea = kitchenArea ?: 0,
    publicArea = publicArea ?: 0,
    seatingCapacity = seatingCapacity ?: 0,
    totalArea = totalArea ?: 0,
)

fun Store.toEntity() = StoreData(
    id = 0,
    uuid = null,
    name = name,
    organization = null,
    areaMetrics = areaMetrics?.toEntity(),
    catalogId = null,
    actualAddress = actualAddress.toString(),
    storeTypes = storeTypes?.toStoreTypeEntity(),
    serviceTypes = serviceTypes?.toServiceTypeEntity(),
    previousErp = previousErp
)

fun StoreType.toEntity() = StoreTypeData.valueOf(name)

fun List<StoreType>.toStoreTypeEntity(): List<StoreTypeData> = map { it.toEntity() }

fun ServiceType.toEntity() = ServiceTypeData.valueOf(name)

fun List<ServiceType>.toServiceTypeEntity(): List<ServiceTypeData> = map { it.toEntity() }

fun StoreType.toDto() = StoreSurveyStoreType.valueOf(name)

fun List<StoreType>.toStoreSurveyType(): List<StoreSurveyStoreType> = map { it.toDto() }

fun ServiceType.toDto() = StoreSurveyStoreService.valueOf(name)

fun List<ServiceType>.toStoreSurveyStoreService(): List<StoreSurveyStoreService> =
    map { it.toDto() }

fun Store.toStoreCreateInput(
    organizationId: UUID,
    catalogId: UUID
): StoreCreateInput {
    return StoreCreateInput(
        name = name,
        currency = Currency.RUB.rawValue,
        address = Input.fromNullable(actualAddress.toString()),
        catalogId = catalogId,
        organizationId = organizationId,
        type = net.nomia.erp.schema.type.StoreType.KAFE,
        characteristics = Input.optional(
            StoreCharacteristicsInput(Input.optional(areaMetrics?.toAreaMetricsInput()))
        ),
        survey = Input.fromNullable(
            StoreSurveyInput(
                previousErp = Input.optional(previousErp),
                storeTypes = storeTypes?.toStoreSurveyType() ?: emptyList(),
                storeServices = serviceTypes?.toStoreSurveyStoreService() ?: emptyList(),

                )
        )
    )
}

fun CreateStoreMutation.Output.toStoreResponse() = StoreResponse(
    id = id,
    name = name
)


fun AreaMetrics.toAreaMetricsInput(): AreaMetricsInput {
    return AreaMetricsInput(
        totalArea = Input.optional(totalArea),
        publicArea = Input.optional(publicArea),
        kitchenArea = Input.optional(kitchenArea),
        seatingCapacity = Input.optional(seatingCapacity)
    )
}

