package net.nomia.pos.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.nomia.auth.domain.LogoutUseCase
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val logoutUseCase : LogoutUseCase
) : ViewModel() {

    fun logOut() = viewModelScope.launch {
        logoutUseCase.invoke()
    }

}
