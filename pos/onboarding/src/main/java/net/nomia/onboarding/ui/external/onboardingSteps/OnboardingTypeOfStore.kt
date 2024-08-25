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
import net.nomia.onboarding.domain.model.Store
import net.nomia.onboarding.domain.model.StoreType
import net.nomia.onboarding.ui.model.StoreTypeWithIcon

@Composable
internal fun OnboardingTypeOfStore(
    modifier: Modifier = Modifier,
    storeTypes: List<StoreTypeWithIcon>,
    store: Store,
    onStoreTypeSelectedListChanged: (List<StoreTypeWithIcon>) -> Unit,
) {

    var checkedStoreTypes by rememberSaveable {
        store.storeTypes?.let {
            mutableStateOf(updateSelectedTypesFromData(it, storeTypes))
        } ?: mutableStateOf(emptyList())
    }

    LaunchedEffect(key1 = checkedStoreTypes) {
        onStoreTypeSelectedListChanged(checkedStoreTypes)
    }

    Column(modifier = modifier) {
        for (storeType in storeTypes) {
            NomiaRightSideCheckboxListItem(
                headlineText = {
                    Text(text = stringResource(id = storeType.storeType.stringResId))
                },
                leadingContent = {
                    Icon(
                        painter = painterResource(id = storeType.iconResId),
                        contentDescription = null
                    )
                },
                checked = checkedStoreTypes.contains(storeType)
            ) {

                if (it) {
                    checkedStoreTypes += storeType
                } else {
                    checkedStoreTypes -= storeType
                }
            }
            Divider()
        }
    }

}

private fun updateSelectedTypesFromData(
    selectedTypes: List<StoreType>,
    allTypesWithIcons: List<StoreTypeWithIcon>
): List<StoreTypeWithIcon> {
    val selectedList = mutableListOf<StoreTypeWithIcon>()
    for(storeType in allTypesWithIcons) {
        if(selectedTypes.contains(storeType.storeType)) {
            selectedList.add(storeType)
        }
    }
    return selectedList
}
