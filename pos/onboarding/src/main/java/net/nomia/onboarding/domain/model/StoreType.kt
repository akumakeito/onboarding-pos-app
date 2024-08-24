package net.nomia.onboarding.domain.model

import androidx.annotation.StringRes
import net.nomia.onboarding.R


enum class StoreType(@StringRes val stringResId : Int )  {
    BAR(R.string.bar),
    CAFE(R.string.cafe),
    COFFEE_HOUSE(R.string.coffee_house),
    CULINARY(R.string.culinary),
    EATERY(R.string.eatery),
    OTHER(R.string.other),
    RESTAURANT(R.string.restaurant)
}
