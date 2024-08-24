package net.nomia.onboarding.ui.external.onboardingSteps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import net.nomia.common.ui.composable.NomiaOutlinedTextField
import net.nomia.common.ui.theme.spacers
import net.nomia.onboarding.R
import net.nomia.onboarding.domain.model.Store

@Composable
internal fun OnboardingSizeOfStore(
    modifier: Modifier = Modifier,
    onTotalAreaChanged: (String) -> Unit,
    onSeatingCapacityChanged: (String) -> Unit,
    onPublicAreaChanged: (String) -> Unit,
    onKitchenAreaChanged: (String) -> Unit,
    store: Store
) {
    var totalAreaState by rememberSaveable {
        store.areaMetrics?.totalArea?.let {
            mutableStateOf(it.toString())
        } ?: mutableStateOf("")
    }
    var seatingCapacityState by rememberSaveable {
        store.areaMetrics?.seatingCapacity?.let {
            mutableStateOf(it.toString())
        } ?: mutableStateOf("")
    }
    var publicAreaState by rememberSaveable {
        store.areaMetrics?.publicArea?.let {
            mutableStateOf(it.toString())
        } ?: mutableStateOf("")
    }
    var kitchenAreaState by rememberSaveable {
        store.areaMetrics?.kitchenArea?.let {
            mutableStateOf(it.toString())
        } ?: mutableStateOf("")
    }

    Column(modifier = modifier.fillMaxWidth()) {
        NomiaOutlinedTextField(
            value = totalAreaState,
            onValueChange = {
                if (it.isDigitsOnly()) {
                    totalAreaState = it
                    onTotalAreaChanged(it)
                }
            },
            label = { Text(text = stringResource(id = R.string.common_square)) },
            placeholder = { Text(text = stringResource(id = R.string.common_square)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))

        NomiaOutlinedTextField(
            value = seatingCapacityState,
            onValueChange = {
                if (it.isDigitsOnly()) {
                    seatingCapacityState = it
                    onSeatingCapacityChanged(it)
                }
            },
            label = { Text(text = stringResource(id = R.string.number_of_seats)) },
            placeholder = { Text(text = stringResource(id = R.string.number_of_seats)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))

        NomiaOutlinedTextField(
            value = publicAreaState,
            onValueChange = {
                if (it.isDigitsOnly()) {
                    publicAreaState = it
                    onPublicAreaChanged(it)
                }
            },
            label = { Text(text = stringResource(id = R.string.hall_square)) },
            placeholder = { Text(text = stringResource(id = R.string.hall_square)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))

        NomiaOutlinedTextField(
            value = kitchenAreaState,
            onValueChange = {
                if (it.isDigitsOnly()) {
                    kitchenAreaState = it
                    onKitchenAreaChanged(it)
                }
            },
            label = { Text(text = stringResource(id = R.string.kitchen_square)) },
            placeholder = { Text(text = stringResource(id = R.string.kitchen_square)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}