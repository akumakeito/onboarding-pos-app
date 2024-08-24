package net.nomia.onboarding.ui.external

fun String.toIntValue(): Int {
    return try {
        this.toInt()
    } catch (e: Exception) {
        0
    }
}