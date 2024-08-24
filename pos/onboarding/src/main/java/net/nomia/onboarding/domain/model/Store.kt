package net.nomia.onboarding.domain.model

data class Store(
    val id: String,
    val name: String,
    val actualAddress: Address,
    val areaMetrics: AreaMetrics? = null,
    val storeTypes: List<StoreType>? = null,
    val serviceTypes: List<ServiceType>? = null,
    val previousErp : String? = null
)