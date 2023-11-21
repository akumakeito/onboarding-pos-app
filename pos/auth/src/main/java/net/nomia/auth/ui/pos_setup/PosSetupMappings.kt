package net.nomia.auth.ui.pos_setup

import net.nomia.main.domain.model.TerminalInput
import net.nomia.auth.ui.pos_setup.model.TerminalValue

@Suppress("ComplexCondition")
fun TerminalValue.toInput() =
    if (name.isNotBlank() && organization != null && store != null && menu != null) {
        TerminalInput(id, name, organization, store, menu, orderSequence)
    } else null
