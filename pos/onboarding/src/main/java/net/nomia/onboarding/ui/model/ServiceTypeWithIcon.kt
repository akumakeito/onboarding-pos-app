package net.nomia.onboarding.ui.model

import androidx.annotation.DrawableRes
import net.nomia.onboarding.domain.model.ServiceType

data class ServiceTypeWithIcon(
    val serviceType : ServiceType,
    @DrawableRes val iconResId : Int)
