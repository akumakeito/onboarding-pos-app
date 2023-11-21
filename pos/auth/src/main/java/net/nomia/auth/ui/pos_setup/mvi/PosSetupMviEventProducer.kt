package net.nomia.auth.ui.pos_setup.mvi


import net.nomia.mvi.Event
import net.nomia.mvi.EventProducer
import net.nomia.auth.ui.pos_setup.model.TerminalState
import net.nomia.auth.ui.pos_setup.mvi.PosSetupMviEffect
import net.nomia.auth.ui.pos_setup.mvi.PosSetupMviEvent

internal object PosSetupMviEventProducer : EventProducer<PosSetupMviEffect, Event> {

    override fun invoke(effect: PosSetupMviEffect): Event? {
        return when(effect) {
            is PosSetupMviEffect.SaveTerminal ->
                if (effect.state is TerminalState.Saved) PosSetupMviEvent.OnTerminalSaved else null
            is PosSetupMviEffect.Logout -> PosSetupMviEvent.OnLoggedOut
            is PosSetupMviEffect.OpenErpExternally ->
                PosSetupMviEvent.OnOpenedErpExternally(effect.serverProvider.url)
            else -> null
        }
    }
}
