package net.nomia.onboarding.ui.external

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import net.nomia.common.ui.composable.NomiaFooter
import net.nomia.common.ui.composable.NomiaScrollableScaffold
import net.nomia.common.ui.theme.NomiaThemeMaterial3
import net.nomia.common.ui.theme.appResources
import net.nomia.common.ui.theme.spacers
import net.nomia.core.ui.preview.PhoneAndTabletPreview
import net.nomia.onboarding.R
import net.nomia.onboarding.ui.composable.BaseOnboardingLayout
import net.nomia.onboarding.ui.composable.OnBoardingFooter
import net.nomia.onboarding.ui.composable.OnboardingContent
import net.nomia.onboarding.ui.composable.OnboardingLineProgress
import net.nomia.onboarding.ui.external.onboardingSteps.OnboardingFirstStore
import net.nomia.onboarding.ui.external.onboardingSteps.OnboardingSizeOfStore
import net.nomia.onboarding.ui.external.onboardingSteps.OnboardingTypeOfServices
import net.nomia.onboarding.ui.external.onboardingSteps.OnboardingTypeOfStore
import net.nomia.onboarding.ui.external.onboardingSteps.OnboardingWelcome
import net.nomia.onboarding.ui.model.ExternalOnboardingUiState
import net.nomia.onboarding.ui.model.OnboardingIntFields
import net.nomia.onboarding.ui.model.OnboardingStringFields


@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val orientation = LocalConfiguration.current.orientation
    val uiState by viewModel.currentOnboardingUiState.collectAsState()
    val canSkipStep = uiState.canSkipStep
    val canGoBack = uiState.canGoBack
    val isNextButtonEnabled by viewModel.isButtonEnabled.collectAsState()
    val stepCount = ExternalOnboardingUiState.values().size
    val isNewStore = viewModel.isNewStore.collectAsState()
    val storeData = viewModel.store.collectAsState()
    val userName = viewModel.userName.collectAsState()
    val userEmailOrPhone = viewModel.userEmailOrPhone.collectAsState()

    LaunchedEffect(orientation) {
        Log.d("OnboardingScreen", "Orientation changed: $orientation")
    }
    NomiaScrollableScaffold(
        title = {
            Icon(
                painter = painterResource(id = MaterialTheme.appResources.textLogoResId),
                tint = Color.Unspecified,
                contentDescription = null,
                modifier = Modifier.padding(start = MaterialTheme.spacers.small)
            )
        },
        actions = {
            if (canSkipStep) {
                SkipTextButton {
                    viewModel.skipStep()
                }
            }
        },
        footer = {
            if (uiState !is ExternalOnboardingUiState.ApplicationIsReady) {
                NomiaFooter(
                    content = {
                        OnBoardingFooter(
                            canGoBack = canGoBack,
                            orientation = orientation,
                            navigateNext = {
                                viewModel.navigateToNextStep()
                            },
                            navigateBack = {
                                viewModel.navigateToPreviousStep()
                            },
                            loading = uiState.loading,
                            isButtonEnabled = isNextButtonEnabled
                        )
                    }
                )
            }
        }
    ) {
        OnboardingLineProgress(currentPage = uiState.numberOfStep, totalPages = stepCount)

        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))

        BaseOnboardingLayout {
            val activity = LocalContext.current as? Activity

            BackHandler {
                when (uiState) {
                    is ExternalOnboardingUiState.Welcome -> activity?.finish()
                    else -> viewModel.navigateToPreviousStep()
                }
            }

            when (uiState) {
                is ExternalOnboardingUiState.Welcome -> {
                    OnboardingContent(
                        title = stringResource(id = uiState.titleResId),
                        subtitle = uiState.subtitleResId?.let { stringResource(id = it) }
                    ) {
                        OnboardingWelcome(
                            onNameChanged = {
                                viewModel.updateStoreStringValue(
                                    OnboardingStringFields.USER_NAME,
                                    it
                                )
                            },
                            onPhoneEmailChanged = {
                                viewModel.updateStoreStringValue(
                                    OnboardingStringFields.USER_EMAIL_OR_PHONE,
                                    it
                                )
                            },
                            name = userName.value,
                            phoneEmail = userEmailOrPhone.value,
                        )
                    }
                }

                is ExternalOnboardingUiState.FirstStore -> {
                    OnboardingContent(
                        title = stringResource(id = uiState.titleResId),
                        subtitle = uiState.subtitleResId?.let { stringResource(id = it) }
                    ) {
                        OnboardingFirstStore(
                            onNameChanged = {
                                viewModel.updateStoreStringValue(
                                    OnboardingStringFields.STORE_NAME,
                                    it
                                )
                            },
                            onCountryCityChanged = {
                                viewModel.updateStoreStringValue(
                                    OnboardingStringFields.COUNTRY_CITY,
                                    it
                                )
                            },
                            onAddressChanged = {
                                viewModel.updateStoreStringValue(
                                    OnboardingStringFields.ADDRESS,
                                    it
                                )
                            },
                            onPreviousErpChanged = {
                                viewModel.updateStoreStringValue(
                                    OnboardingStringFields.PREVIOUS_ERP,
                                    it
                                )
                            },
                            onNewStoreCheckChanged = {
                                viewModel.updateIsNewStore(it)
                            },
                            storeData = storeData.value,
                            isNewStore = isNewStore.value
                        )
                    }
                }

                is ExternalOnboardingUiState.TypeOfStore ->
                    OnboardingContent(
                        title = stringResource(id = uiState.titleResId),
                        subtitle = uiState.subtitleResId?.let { stringResource(id = it) }
                    ) {
                        OnboardingTypeOfStore(
                            storeTypes =
                            (uiState as ExternalOnboardingUiState.TypeOfStore).storeTypeList,
                            store = storeData.value
                        ) {
                            Log.d("OnboardingViewModel", "updateStoreTypes from mainscreen $it")
                            viewModel.updateStoreTypes(it)
                        }
                    }

                is ExternalOnboardingUiState.SizeOfStore ->
                    OnboardingContent(
                        title = stringResource(id = uiState.titleResId),
                    ) {
                        OnboardingSizeOfStore(
                            onTotalAreaChanged = {
                                viewModel.updateStoreIntValue(
                                    OnboardingIntFields.TOTAL_AREA,
                                    it.toIntValue()
                                )
                            },
                            onPublicAreaChanged = {
                                viewModel.updateStoreIntValue(
                                    OnboardingIntFields.PUBLIC_AREA,
                                    it.toIntValue()
                                )
                            },
                            onKitchenAreaChanged = {
                                viewModel.updateStoreIntValue(
                                    OnboardingIntFields.KITCHEN_AREA,
                                    it.toIntValue()
                                )
                            },
                            onSeatingCapacityChanged = {
                                viewModel.updateStoreIntValue(
                                    OnboardingIntFields.SEATING_CAPACITY,
                                    it.toIntValue()
                                )
                            },
                            store = storeData.value

                        )
                    }

                is ExternalOnboardingUiState.TypeOfServices ->
                    OnboardingContent(
                        title = stringResource(id = uiState.titleResId),
                    ) {
                        OnboardingTypeOfServices(
                            serviceTypeList =
                            (uiState as ExternalOnboardingUiState.TypeOfServices).serviceTypeList,
                            store = storeData.value
                        ) {
                            viewModel.updateServiceTypes(it)
                        }
                    }

                is ExternalOnboardingUiState.ApplicationIsReady ->
                    OnboardingContent(
                        title = stringResource(id = uiState.titleResId),
                        subtitle = uiState.subtitleResId?.let { stringResource(id = it) }
                    ) { }
            }
        }
    }
}


@Composable
private fun SkipTextButton(
    onSkip: () -> Unit
) {
    TextButton(onClick = { onSkip() }) {
        Text(
            text = stringResource(id = R.string.skip),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}


@PhoneAndTabletPreview
@Composable
private fun OnboardingPreview() {
    NomiaThemeMaterial3 {
        Surface {
            OnboardingScreen()
        }
    }
}
