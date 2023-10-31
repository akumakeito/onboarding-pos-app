package net.nomia.pos.ui.auth.pos_setup.model

sealed class TerminalState {
    object Empty : TerminalState()
    object Processing : TerminalState()
    data class Error(val message: String) : TerminalState()
    object Saved : TerminalState()
}
