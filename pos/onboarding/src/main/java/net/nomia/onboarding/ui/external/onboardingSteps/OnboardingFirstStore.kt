package net.nomia.onboarding.ui.external.onboardingSteps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import net.nomia.common.ui.composable.NomiaOutlinedTextField
import net.nomia.common.ui.composable.list_item.NomiaCheckboxListItem
import net.nomia.common.ui.theme.spacers
import net.nomia.onboarding.R
import net.nomia.onboarding.domain.model.Store

@Composable
fun OnboardingFirstStore(
    modifier: Modifier = Modifier,
    onNewStoreCheckChanged : (Boolean) -> Unit,
    onNameChanged: (String) -> Unit,
    onCountryCityChanged: (String) -> Unit,
    onAddressChanged: (String) -> Unit,
    onPreviousErpChanged: (String) -> Unit,
    storeData : Store,
    isNewStore : Boolean
) {

    var checkedState by rememberSaveable {
        mutableStateOf(isNewStore)
    }

    var nameState by rememberSaveable {
        mutableStateOf(storeData.name)
    }

    var countryCityState by rememberSaveable {
        mutableStateOf(storeData.actualAddress.countryAndCity)
    }

    var addressState by rememberSaveable {
        mutableStateOf(storeData.actualAddress.address)
    }

    var previousErp by rememberSaveable {
        mutableStateOf(storeData.previousErp ?: "")
    }

    LaunchedEffect(key1 = storeData.previousErp) {
        previousErp = storeData.previousErp ?: ""
    }

    Column(modifier = modifier.fillMaxWidth()) {
        NomiaOutlinedTextField(
            value = nameState,
            onValueChange = {
                nameState = it
                onNameChanged(it)
            },
            label = { Text(text = stringResource(id = R.string.store_name)) },
            placeholder = { Text(text = stringResource(id = R.string.store_name)) },
            supportingText = { Text(text = stringResource(id = R.string.you_can_add_more_store)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))

        NomiaOutlinedTextField(
            value = countryCityState,
            onValueChange = {
                countryCityState = it
                onCountryCityChanged(it)
            },
            label = { Text(text = stringResource(id = R.string.country_and_city)) },
            placeholder = { Text(text = stringResource(id = R.string.country_and_city)) },
            supportingText = { Text(text = stringResource(id = R.string.for_timestamp)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))

        NomiaOutlinedTextField(
            value = addressState,
            onValueChange = {
                addressState = it
                onAddressChanged(it)
            },
            label = { Text(text = stringResource(id = R.string.address)) },
            placeholder = { Text(text = stringResource(id = R.string.address)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))


        NomiaCheckboxListItem(
            checked = checkedState,
            headlineText = { Text(text = stringResource(id = R.string.new_store)) },
            supportingText = { Text(text = stringResource(id = R.string.check_if_this_is_a_new_store)) },
        ) {
            checkedState = it
            onNewStoreCheckChanged(it)
        }

        if (!checkedState) {

            Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))

            NomiaOutlinedTextField(
                value = previousErp,
                onValueChange = {
                    previousErp = it
                    onPreviousErpChanged(it)
                },
                label = { Text(text = stringResource(id = R.string.automatization_system)) },
                placeholder = { Text(text = stringResource(id = R.string.automatization_system)) },
                supportingText = { Text(text = stringResource(id = R.string.automatization_system_name)) },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Done
                ),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}