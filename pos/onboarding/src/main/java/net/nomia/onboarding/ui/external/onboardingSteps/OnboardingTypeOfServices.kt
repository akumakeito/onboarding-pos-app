package net.nomia.onboarding.ui.external.onboardingSteps

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import net.nomia.common.ui.composable.list_item.NomiaRightSideCheckboxListItem
import net.nomia.onboarding.domain.model.ServiceType
import net.nomia.onboarding.domain.model.Store
import net.nomia.onboarding.ui.model.ServiceTypeWithIcon

@Composable
fun OnboardingTypeOfServices(
    serviceTypeList : List<ServiceTypeWithIcon>,
    modifier: Modifier = Modifier,
    store : Store,
    onServiceTypeSelectedListChanged: (List<ServiceTypeWithIcon>) -> Unit
) {

    var checkedServiceTypes by rememberSaveable {
        store.serviceTypes?.let {
            mutableStateOf(updateSelectedTypesFromData(it, serviceTypeList))
        } ?: mutableStateOf(emptyList())
    }

    LaunchedEffect(key1 = checkedServiceTypes) {
        onServiceTypeSelectedListChanged(checkedServiceTypes)
    }

    Column(modifier = modifier) {
        for(serviceType in serviceTypeList) {
            NomiaRightSideCheckboxListItem(
                headlineText = {
                    Text(text = stringResource(id = serviceType.serviceType.stringResId))
                },
                leadingContent = {
                    Icon(
                        painter = painterResource(id = serviceType.iconResId),
                        contentDescription = null
                    )
                },
                checked = checkedServiceTypes.contains(serviceType)
            ) {
                if (it) {
                    checkedServiceTypes += serviceType
                } else {
                    checkedServiceTypes -= serviceType
                }
            }
            Divider()
        }
    }


}

private fun updateSelectedTypesFromData(
    selectedTypes: List<ServiceType>,
    allTypesWithIcons: List<ServiceTypeWithIcon>
): List<ServiceTypeWithIcon> {
    val selectedList = mutableListOf<ServiceTypeWithIcon>()
    for(storeType in allTypesWithIcons) {
        if(selectedTypes.contains(storeType.serviceType)) {
            selectedList.add(storeType)
        }
    }
    return selectedList
}