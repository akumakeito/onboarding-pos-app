package net.nomia.pos.ui.auth.pos_setup.mvi

import net.nomia.common.data.model.Menu
import net.nomia.common.data.model.Organization
import net.nomia.common.data.model.Store
import net.nomia.pos.core.text.Content
import net.nomia.pos.ui.auth.pos_setup.model.EnabledList
import net.nomia.pos.ui.auth.pos_setup.model.TerminalState
import net.nomia.pos.ui.auth.pos_setup.model.TerminalValue
import net.nomia.pos.ui.auth.pos_setup.model.emptyEnabledList
import net.nomia.pos.ui.auth.pos_setup.toInput
import net.nomia.settings.domain.model.ServerProvider


internal data class PosSetupMviState(
    val terminalState: TerminalState,
    val terminalValue: TerminalValue,
    val organizations: EnabledList<Organization>,
    val stores: EnabledList<Store>,
    val menus: EnabledList<Menu>,
    val hasNoMenusCreated: Boolean,
    val error: Content?,
    val serverProvider: ServerProvider,
) {
    companion object {
        val INITIAL = PosSetupMviState(
            terminalState = TerminalState.Empty,
            terminalValue = TerminalValue(),
            organizations = emptyEnabledList(false),
            stores = emptyEnabledList(false),
            menus = emptyEnabledList(false),
            hasNoMenusCreated = false,
            error = null,
            serverProvider = ServerProvider.Custom(),
        )
    }

    fun isValid() : Boolean = terminalValue.toInput() != null
}
