package com.dfavilav.zararickmorty.presentation.common

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.dfavilav.zararickmorty.R
import com.dfavilav.zararickmorty.domain.model.Character
import com.dfavilav.zararickmorty.ui.theme.DarkGray
import com.dfavilav.zararickmorty.ui.theme.LightGray
import com.dfavilav.zararickmorty.ui.theme.NETWORK_ERROR_ICON_HEIGHT
import com.dfavilav.zararickmorty.ui.theme.SMALL_PADDING
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun EmptyScreen(
    error: LoadState.Error? = null,
    characters: LazyPagingItems<Character>? = null
) {
    var message by remember {
        mutableStateOf("Find your Favorite character of Rick&Morty!")
    }
    var icon by remember {
        mutableIntStateOf(R.drawable.ic_error)
    }

    if (error != null) {
        message = parseErrorMessage(error = error)
        icon = R.drawable.ic_error
    }

    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) ContentAlpha.disabled else 0f,
        animationSpec = tween(
            durationMillis = 1000
        ),
        label = "Alpha Animation"
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    EmptyContent(
        alphaAnim = alphaAnim,
        icon = icon,
        message = message,
        characters = characters,
        error = error
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmptyContent(
    alphaAnim: Float,
    icon: Int,
    message: String,
    error: LoadState.Error? = null,
    characters: LazyPagingItems<Character>? = null
) {
    var isRefreshing by remember { mutableStateOf(false) }
    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            characters?.refresh()
            isRefreshing = false
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(state = refreshState, enabled = error != null),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        PullRefreshIndicator(
            state = refreshState,
            refreshing = isRefreshing
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .size(NETWORK_ERROR_ICON_HEIGHT),
                painter = painterResource(id = icon),
                contentDescription = stringResource(R.string.network_error_icon),
            )
            Text(
                modifier = Modifier
                    .padding(top = SMALL_PADDING)
                    .alpha(alpha = alphaAnim),
                text = message,
                color = if (isSystemInDarkTheme()) LightGray else DarkGray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            )
        }
    }
//    }
}

fun parseErrorMessage(error: LoadState.Error): String {
    return when (error.error) {
        is SocketTimeoutException -> {
            "Server Unavailable."
        }
        is ConnectException -> {
            "Internet Unavailable."
        }
        else -> {
            "Unknown Error."
        }
    }
}

@Composable
@Preview(showBackground = true)
fun EmptyScreenPreview() {
    EmptyContent(
        alphaAnim = ContentAlpha.disabled,
        icon = R.drawable.ic_error,
        message = "Internet Unavailable."
    )
}

@Composable
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun EmptyScreenDarkPreview() {
    EmptyContent(
        alphaAnim = ContentAlpha.disabled,
        icon = R.drawable.ic_error,
        message = "Internet Unavailable."
    )
}