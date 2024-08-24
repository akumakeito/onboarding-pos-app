package net.nomia.onboarding.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.nomia.common.ui.previews.ThemePreviews
import net.nomia.common.ui.theme.NomiaThemeMaterial3

@Composable
internal fun OnboardingLineProgress(
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier,

) {
    var currentPageState by rememberSaveable {
        mutableStateOf(currentPage)
    }
    var totalPagesState by rememberSaveable {
        mutableStateOf(totalPages)
    }

    LaunchedEffect(key1 = currentPage) {
        currentPageState = currentPage
    }

    LaunchedEffect(key1 = totalPages) {
        totalPagesState = totalPages
    }


    SegmentedProgressBar(segments = totalPagesState, progress = currentPageState, modifier = modifier)


}

@Composable
private fun SegmentedProgressBar(
    segments: Int,
    progress: Int,
    modifier: Modifier = Modifier,
    completedColor: Color = MaterialTheme.colorScheme.primary,
    incompleteColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    height: Dp = 4.dp,
    spaceBetweenSegments: Dp = 4.dp,
) {

    Row(
        modifier = modifier.height(height),
        horizontalArrangement = Arrangement.spacedBy(spaceBetweenSegments),
    ) {
        for (index in 0 until segments) {

            val color = if (index < progress) completedColor else incompleteColor

            Box(
                modifier = Modifier
                    .background(color)
                    .fillMaxHeight()
                    .weight(1f),
            )
        }
    }
}


@ThemePreviews
@Composable
private fun OnboardingLineProgressPreview() {
    NomiaThemeMaterial3 {
        Surface {
            OnboardingLineProgress(3, 6)
        }
    }
}