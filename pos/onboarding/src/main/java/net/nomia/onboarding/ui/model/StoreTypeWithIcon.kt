package net.nomia.onboarding.ui.model

import androidx.annotation.DrawableRes
import net.nomia.onboarding.domain.model.StoreType

data class StoreTypeWithIcon(
    val storeType : StoreType,
    @DrawableRes val iconResId : Int,
)