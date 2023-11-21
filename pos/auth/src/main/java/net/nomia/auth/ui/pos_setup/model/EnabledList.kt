package net.nomia.auth.ui.pos_setup.model

internal class EnabledList<T>(
    private val wrapped: List<T>,
    val enabled: Boolean,
    val isError: Boolean,
    ) : List<T> by wrapped

internal fun <T> emptyEnabledList(
    enabled: Boolean,
    isError: Boolean = false
): EnabledList<T> = EnabledList(emptyList(), enabled, isError)
