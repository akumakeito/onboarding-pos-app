package net.nomia.onboarding.ui.composable

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import net.nomia.common.ui.composable.NomiaFilledButton
import net.nomia.common.ui.composable.NomiaOutlinedButton
import net.nomia.common.ui.composable.NomiaOutlinedIconButton
import net.nomia.common.ui.composable.NomiaSpinner
import net.nomia.common.ui.theme.spacers
import net.nomia.onboarding.R

@Composable
internal fun OnBoardingFooter(
    canGoBack: Boolean,
    orientation: Int,
    isButtonEnabled: Boolean,
    loading : Boolean = false,
    navigateNext: () -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Row(
            modifier = modifier.width(baseLayoutWidth),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (canGoBack) {
                when (orientation) {

                    Configuration.ORIENTATION_PORTRAIT -> NomiaOutlinedIconButton(onClick = { navigateBack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_24),
                            contentDescription = null
                        )
                    }

                    Configuration.ORIENTATION_LANDSCAPE -> NomiaOutlinedButton(onClick = { navigateBack() }) {
                        Text(text = stringResource(id = R.string.back_action))
                    }

                }
            }
            Spacer(modifier = Modifier.width(MaterialTheme.spacers.small))

            NomiaFilledButton(
                onClick = { navigateNext() },
                modifier = Modifier.weight(1f),
                enabled = isButtonEnabled
            ) {
                if (loading) {
                    NomiaSpinner()
                } else {
                    Text(text = stringResource(id = R.string.continue_action))
                }
            }
        }
    }
}