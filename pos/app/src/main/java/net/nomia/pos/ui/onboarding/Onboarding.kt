package net.nomia.pos.ui.onboarding

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import net.nomia.common.ui.composable.NomiaScrollableScaffold
import net.nomia.common.ui.composable.ScreenTitleText
import net.nomia.pos.R


@Composable
fun Onboarding(
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    NomiaScrollableScaffold(
        title = { ScreenTitleText(text = stringResource(id = R.string.onboarding)) },
        actions = {
            IconButton(
                onClick = viewModel::logOut
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logout_24),
                    contentDescription = null
                )
            }
        },
    ) {
        /**
         * YOUR CODE GOES HERE
         */
    }
}
