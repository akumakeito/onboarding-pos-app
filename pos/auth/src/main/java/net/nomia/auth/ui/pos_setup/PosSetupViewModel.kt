package net.nomia.auth.ui.pos_setup

import dagger.hilt.android.lifecycle.HiltViewModel
import net.nomia.mvi.MviViewModel
import net.nomia.auth.ui.pos_setup.mvi.PosSetupMviAction
import net.nomia.auth.ui.pos_setup.mvi.PosSetupMviFeatureFactory
import net.nomia.auth.ui.pos_setup.mvi.PosSetupMviState
import javax.inject.Inject

@HiltViewModel
internal class PosSetupViewModel @Inject constructor(
    factory: PosSetupMviFeatureFactory,
) : MviViewModel<PosSetupMviState, PosSetupMviAction>(factory)
