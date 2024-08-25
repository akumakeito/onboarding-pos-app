package net.nomia.onboarding.ui.external

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.nomia.onboarding.domain.USER_EMAIL_OR_PHONE
import net.nomia.onboarding.domain.USER_NAME
import net.nomia.onboarding.domain.model.Address
import net.nomia.onboarding.domain.model.AreaMetrics
import net.nomia.onboarding.domain.model.Store
import net.nomia.onboarding.domain.repository.CreateStoreUseCase
import net.nomia.onboarding.domain.repository.SaveUserDataUseCase
import net.nomia.onboarding.ui.model.ExternalOnboardingUiState
import net.nomia.onboarding.ui.model.OnboardingIntFields
import net.nomia.onboarding.ui.model.OnboardingStringFields
import net.nomia.onboarding.ui.model.ServiceTypeWithIcon
import net.nomia.onboarding.ui.model.StoreTypeWithIcon
import net.nomia.pos.core.data.Response
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val createStoreUseCase: CreateStoreUseCase,
    private val saveUserDataUseCase: SaveUserDataUseCase,
) : ViewModel() {
    private val emptyStore = Store(
        id = "",
        name = "",
        actualAddress = Address("", ""),
        previousErp = "",
        storeTypes = emptyList(),
        serviceTypes = emptyList(),
        areaMetrics = AreaMetrics()
    )

    private val _currentOnboardingUiState = MutableStateFlow<ExternalOnboardingUiState>(
        ExternalOnboardingUiState.Welcome()
    )
    val currentOnboardingUiState = _currentOnboardingUiState.asStateFlow()

    private val _isNewStore = MutableStateFlow(true)
    val isNewStore = _isNewStore.asStateFlow()

    private val _userName = MutableStateFlow("")
    val userName = _userName.asStateFlow()
    private val _userEmailOrPhone = MutableStateFlow("")
    val userEmailOrPhone = _userEmailOrPhone.asStateFlow()

    private val _store = MutableStateFlow(emptyStore)
    val store = _store.asStateFlow()

    private val _isButtonEnabled = MutableStateFlow(false)
    val isButtonEnabled = _isButtonEnabled.asStateFlow()

    fun updateIsNewStore(isNewStore: Boolean) {
        _isNewStore.value = isNewStore
        if (isNewStore) {
            _store.update {
                it.copy(previousErp = null)
            }
        }
        updateButtonEnabled()
    }

    fun skipStep() {
        clearScreenInput()
        navigateToNextStep()
    }

    private fun clearScreenInput() {
        _store.update {
            when (_currentOnboardingUiState.value) {
                is ExternalOnboardingUiState.SizeOfStore -> it.copy(areaMetrics = AreaMetrics())
                is ExternalOnboardingUiState.TypeOfServices -> it.copy(serviceTypes = emptyList())
                is ExternalOnboardingUiState.TypeOfStore -> it.copy(storeTypes = emptyList())
                else -> it
            }
        }
    }

    fun navigateToNextStep() {

        if (_currentOnboardingUiState.value.numberOfStep == ExternalOnboardingUiState.values().size - 1) {
            createStoreAndSaveUserData()
        } else {
            ExternalOnboardingUiState.values()
                .find { it.numberOfStep == _currentOnboardingUiState.value.numberOfStep + 1 }?.let {
                    _currentOnboardingUiState.value = it
                }
        }
        updateButtonEnabled()
    }

    fun navigateToPreviousStep() {
        ExternalOnboardingUiState.values()
            .find { it.numberOfStep == _currentOnboardingUiState.value.numberOfStep - 1 }?.let {
                _currentOnboardingUiState.value = it
            }
        updateButtonEnabled()
    }

    private fun updateButtonEnabled() {
        _store.value.let { store ->
            when (_currentOnboardingUiState.value) {
                is ExternalOnboardingUiState.ApplicationIsReady -> {}
                is ExternalOnboardingUiState.FirstStore -> {
                    if (_isNewStore.value) {
                        checkState(
                            store.name,
                            store.actualAddress.countryAndCity,
                            store.actualAddress.address
                        )
                    } else {
                        checkState(
                            store.name,
                            store.actualAddress.countryAndCity,
                            store.actualAddress.address, store.previousErp
                        )
                    }
                }

                is ExternalOnboardingUiState.SizeOfStore -> {
                    checkState(
                        store.areaMetrics?.totalArea,
                        store.areaMetrics?.publicArea,
                        store.areaMetrics?.kitchenArea,
                        store.areaMetrics?.seatingCapacity
                    )
                }

                is ExternalOnboardingUiState.TypeOfServices -> {
                    checkState(store.serviceTypes)
                }

                is ExternalOnboardingUiState.TypeOfStore -> checkState(store.storeTypes)
                is ExternalOnboardingUiState.Welcome -> checkState(
                    _userName.value,
                    _userEmailOrPhone.value
                )
            }
        }
    }

    fun updateStoreStringValue(field: OnboardingStringFields, value: String) {
        when (field) {
            OnboardingStringFields.STORE_NAME ->
                _store.update {
                    it.copy(name = value)
                }

            OnboardingStringFields.COUNTRY_CITY -> {
                _store.update {
                    it.copy(actualAddress = it.actualAddress.copy(countryAndCity = value))
                }
            }

            OnboardingStringFields.ADDRESS -> {
                _store.update {
                    it.copy(actualAddress = it.actualAddress.copy(address = value))
                }
            }

            OnboardingStringFields.PREVIOUS_ERP -> {
                _store.update {
                    it.copy(previousErp = value)
                }
            }

            OnboardingStringFields.USER_NAME -> _userName.value = value
            OnboardingStringFields.USER_EMAIL_OR_PHONE -> _userEmailOrPhone.value = value
        }
        updateButtonEnabled()
    }

    fun updateStoreIntValue(field: OnboardingIntFields, value: Int) {
        when (field) {
            OnboardingIntFields.SEATING_CAPACITY -> {
                _store.update {
                    it.copy(areaMetrics = it.areaMetrics?.copy(seatingCapacity = value))
                }
            }

            OnboardingIntFields.TOTAL_AREA -> {
                _store.update {
                    it.copy(areaMetrics = it.areaMetrics?.copy(totalArea = value))
                }
            }

            OnboardingIntFields.PUBLIC_AREA -> {
                _store.update {
                    it.copy(areaMetrics = it.areaMetrics?.copy(publicArea = value))
                }
            }

            OnboardingIntFields.KITCHEN_AREA -> {
                _store.update {
                    it.copy(areaMetrics = it.areaMetrics?.copy(kitchenArea = value))
                }
            }
        }
        updateButtonEnabled()
    }


    fun updateStoreTypes(storeTypeList: List<StoreTypeWithIcon>) {
        val newStoreTypeList = storeTypeList.map { it.storeType }
        _store.update {
            it.copy(storeTypes = newStoreTypeList)
        }
        updateButtonEnabled()

    }

    fun updateServiceTypes(serviceTypeList: List<ServiceTypeWithIcon>) {
        val newServiceTypeList = serviceTypeList.map { it.serviceType }
        _store.update {
            it.copy(serviceTypes = newServiceTypeList)
        }
        updateButtonEnabled()

    }


    private fun updateUserData(key: String, value: String) = viewModelScope.launch {
        when (key) {
            USER_NAME -> saveUserDataUseCase(USER_NAME, value)
            USER_EMAIL_OR_PHONE -> saveUserDataUseCase(USER_EMAIL_OR_PHONE, value)
            else -> {}
        }
    }

    private fun createStoreAndSaveUserData() = viewModelScope.launch {

        _currentOnboardingUiState.value = ExternalOnboardingUiState.TypeOfServices(loading = true)

        updateUserData(USER_NAME, _userName.value)
        updateUserData(USER_EMAIL_OR_PHONE, _userEmailOrPhone.value)
        val result = createStoreUseCase(_store.value)

        when (result) {
            is Response.Success<*> -> {
                _currentOnboardingUiState.update {
                    ExternalOnboardingUiState.ApplicationIsReady()
                }
            }
            is Response.Error -> {
                _currentOnboardingUiState.value = ExternalOnboardingUiState.TypeOfServices(
                    loading = false,
                    errorMessage = result.message
                )
            }
            else -> {}
        }
    }

    private fun checkState(vararg args: Any?) {
        _isButtonEnabled.value = args.all { arg ->
            when (arg) {
                is String -> arg.isNotBlank()
                is Int -> arg != 0
                is List<*> -> arg.isNotEmpty()
                else -> false
            }
        }
    }
}



