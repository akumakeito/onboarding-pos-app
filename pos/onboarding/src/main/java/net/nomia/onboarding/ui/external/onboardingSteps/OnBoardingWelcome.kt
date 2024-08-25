package net.nomia.onboarding.ui.external.onboardingSteps

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import net.nomia.common.ui.composable.NomiaOutlinedTextField
import net.nomia.common.ui.theme.spacers
import net.nomia.onboarding.R

@Composable
fun OnboardingWelcome(
    modifier: Modifier = Modifier,
    name : String = "",
    phoneEmail : String = "",
    onNameChanged: (String) -> Unit,
    onPhoneEmailChanged: (String) -> Unit,
    onDone : () -> Unit
) {

    var nameState by rememberSaveable {
        mutableStateOf(name)
    }

    var phoneEmailState by rememberSaveable {
        mutableStateOf(phoneEmail)
    }

    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(modifier = modifier.fillMaxWidth()) {

        NomiaOutlinedTextField(
            value = nameState,
            onValueChange = {
                nameState = it
                onNameChanged(it)
            },
            label = { Text(text = stringResource(id = R.string.your_name)) },
            placeholder = { Text(text = stringResource(id = R.string.your_name)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            focusRequester = focusRequester,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(MaterialTheme.spacers.medium))

        NomiaOutlinedTextField(
            value = phoneEmailState,
            onValueChange = {
                phoneEmailState = it
                onPhoneEmailChanged(it)
            },
            label = { Text(text = stringResource(id = R.string.phone_or_email)) },
            placeholder = { Text(text = stringResource(id = R.string.phone_or_email)) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDone()
                }
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

