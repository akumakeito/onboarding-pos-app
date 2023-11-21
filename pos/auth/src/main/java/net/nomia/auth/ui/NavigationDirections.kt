@file:Suppress("unused")

package net.nomia.auth.ui

import com.compose.type_safe_args.annotation.ComposeDestination

@ComposeDestination
interface PosSetupDestination {
    companion object
}

@ComposeDestination
interface ExternalAuthDestination {
    companion object
}

@ComposeDestination
interface InternalAuthDestination {
    companion object
}
