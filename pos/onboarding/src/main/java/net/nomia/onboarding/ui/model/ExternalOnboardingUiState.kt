package net.nomia.onboarding.ui.model

import androidx.annotation.StringRes
import net.nomia.onboarding.R
import net.nomia.onboarding.domain.model.ServiceType
import net.nomia.onboarding.domain.model.StoreType
import net.nomia.pos.core.text.Content

sealed interface ExternalOnboardingUiState {
    @get:StringRes
    val titleResId: Int

    @get:StringRes
    val subtitleResId: Int?
    val numberOfStep: Int
    val canSkipStep: Boolean
    val canGoBack: Boolean
    val loading: Boolean

    data class Welcome(
        override val loading: Boolean = false,
        val errorMessage: Content? = null,
        override val titleResId: Int = R.string.welcome_title,
        override val subtitleResId: Int = R.string.onboarding_welcome_subtitle,
        override val numberOfStep: Int = 1,
        override val canSkipStep: Boolean = false,
        override val canGoBack: Boolean = false,
    ) : ExternalOnboardingUiState

    data class FirstStore(
        override val loading: Boolean = false,
        val errorMessage: Content? = null,
        override val titleResId: Int = R.string.create_first_store,
        override val subtitleResId: Int = R.string.you_can_change_data_in_settings,
        override val numberOfStep: Int = 2,
        override val canSkipStep: Boolean = false,
        override val canGoBack: Boolean = true,
    ) : ExternalOnboardingUiState

    data class TypeOfStore(
        override val loading: Boolean = false,
        val errorMessage: Content? = null,
        val storeTypeList: List<StoreTypeWithIcon> = storeTypesWithIcon,
        override val titleResId: Int = R.string.whats_your_type_of_store,
        override val subtitleResId: Int = R.string.tell_about_your_business,
        override val numberOfStep: Int = 3,
        override val canSkipStep: Boolean = true,
        override val canGoBack: Boolean = true,
    ) : ExternalOnboardingUiState

    data class SizeOfStore(
        override val loading: Boolean = false,
        val errorMessage: Content? = null,
        override val titleResId: Int = R.string.whats_your_size_of_store,
        override val subtitleResId: Int? = null,
        override val numberOfStep: Int = 4,
        override val canSkipStep: Boolean = true,
        override val canGoBack: Boolean = true,
    ) : ExternalOnboardingUiState

    data class TypeOfServices(
        override val loading: Boolean = false,
        val errorMessage: Content? = null,
        val serviceTypeList: List<ServiceTypeWithIcon> = serviceTypesWithIcon,
        override val titleResId: Int = R.string.what_are_your_services,
        override val subtitleResId: Int? = null,
        override val numberOfStep: Int = 5,
        override val canSkipStep: Boolean = true,
        override val canGoBack: Boolean = true,
    ) : ExternalOnboardingUiState

    data class ApplicationIsReady(
        override val loading: Boolean = false,
        val errorMessage: Content? = null,
        override val titleResId: Int = R.string.application_is_ready,
        override val subtitleResId: Int = R.string.we_will_connect_soon,
        override val numberOfStep: Int = 6,
        override val canSkipStep: Boolean = false,
        override val canGoBack: Boolean = false,
    ) : ExternalOnboardingUiState

    companion object {
        fun values() = listOf(
            Welcome(),
            FirstStore(),
            TypeOfStore(),
            SizeOfStore(),
            TypeOfServices(),
            ApplicationIsReady()
        )
    }
}


private val storeTypesWithIcon = listOf(
    StoreTypeWithIcon(StoreType.RESTAURANT, R.drawable.ic_restaurant_24),
    StoreTypeWithIcon(StoreType.BAR, R.drawable.ic_bar_24),
    StoreTypeWithIcon(StoreType.CAFE, R.drawable.ic_cafe_24),
    StoreTypeWithIcon(StoreType.EATERY, R.drawable.ic_eatery_24),
    StoreTypeWithIcon(StoreType.COFFEE_HOUSE, R.drawable.ic_coffee_shop_24),
    StoreTypeWithIcon(StoreType.CULINARY, R.drawable.ic_culinary_24),
    StoreTypeWithIcon(StoreType.OTHER, R.drawable.ic_other_store_24)
)

private val serviceTypesWithIcon = listOf(
    ServiceTypeWithIcon(ServiceType.TAKEAWAY, R.drawable.ic_service_type_to_go_24),
    ServiceTypeWithIcon(ServiceType.HERE, R.drawable.ic_service_type_in_store_24),
    ServiceTypeWithIcon(ServiceType.DELIVERY, R.drawable.ic_service_type_delivery_24)
)
