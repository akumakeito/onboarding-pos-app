package net.nomia.onboarding.domain.model

import androidx.annotation.StringRes
import net.nomia.onboarding.R


enum class ServiceType (@StringRes val stringResId : Int ) {
    DELIVERY(R.string.delivery),
    HERE(R.string.here),
    TAKEAWAY(R.string.takeaway)
}
