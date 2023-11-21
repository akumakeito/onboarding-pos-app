package net.nomia.auth.ui.pos_setup.mvi


import net.nomia.auth.ui.pos_setup.mvi.*
import net.nomia.mvi.Event
import net.nomia.mvi.MviFeatureFactory
import net.nomia.auth.ui.pos_setup.mvi.PosSetupMviState

internal class PosSetupMviFeatureFactory(
    bootstrap: PosSetupMviBootstrap,
    actor: PosSetupMviActor,
) : MviFeatureFactory<PosSetupMviAction, PosSetupMviEffect, PosSetupMviState, Event>(
    initialState = PosSetupMviState.INITIAL,
    bootstrap = bootstrap,
    actor = actor,
    eventProducer = PosSetupMviEventProducer,
    reducer = PosSetupMviReducer,
    tagPostfix = "PosSetupMviFeature"
)
