package net.nomia.onboarding.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import net.nomia.common.ui.theme.spacers

@Composable
fun OnboardingContent(
    title: String,
    subtitle: String? = "",
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier.padding(horizontal = MaterialTheme.spacers.medium).fillMaxWidth()) {
        TitleAndSubtitle(title = title, subtitle = subtitle)
        Spacer(modifier = Modifier.height(MaterialTheme.spacers.large))

        content()
    }


}

@Composable
private fun TitleAndSubtitle(
    title: String,
    subtitle: String?
) {
    Column {
        ScreenTitleText(text = title)
        if (subtitle.isNullOrBlank().not()) {
            Spacer(modifier = Modifier.height(MaterialTheme.spacers.small))
            ScreenSubtitleText(text = subtitle)
        }
    }
}

@Composable
private fun ScreenTitleText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Start,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.headlineSmall,
    )
}

@Composable
private fun ScreenSubtitleText(
    text: String?,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text ?: "",
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyLarge,
    )
}
