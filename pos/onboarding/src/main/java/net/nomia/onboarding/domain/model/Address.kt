package net.nomia.onboarding.domain.model

data class Address(
    val countryAndCity : String,
    val address : String
) {
    override fun toString(): String = "$countryAndCity, $address"
}