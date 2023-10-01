package com.dfavilav.zararickmorty.presentation.common

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dfavilav.zararickmorty.R
import com.dfavilav.zararickmorty.domain.model.Character
import com.dfavilav.zararickmorty.navigation.Screen
import com.dfavilav.zararickmorty.presentation.components.ShimmerEffect
import com.dfavilav.zararickmorty.ui.theme.Blue200
import com.dfavilav.zararickmorty.ui.theme.CHARACTER_ITEM_HEIGHT
import com.dfavilav.zararickmorty.ui.theme.LARGE_PADDING
import com.dfavilav.zararickmorty.ui.theme.MEDIUM_PADDING
import com.dfavilav.zararickmorty.ui.theme.SMALL_PADDING
import com.dfavilav.zararickmorty.ui.theme.topAppBarContentColor

@ExperimentalCoilApi
@Composable
fun ListContent(
    characters: LazyPagingItems<Character>,
    navController: NavHostController
) {
    val result = handlePagingResult(characters)

    if (result) {
        LazyColumn(
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            items(
                items = characters.itemSnapshotList.items,
                key = { character ->
                    character.id
                }
            ) { characters ->
                CharacterItem(characters, navController = navController)
            }
        }
    }
}

@Composable
fun handlePagingResult(
    characters: LazyPagingItems<Character>
): Boolean {
    characters.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                false
            }
            error != null -> {
                EmptyScreen(error, characters)
                false
            }
            characters.itemCount < 1 -> {
                EmptyScreen()
                false
            }
            else -> true
        }
    }
}

@ExperimentalCoilApi
@Composable
fun CharacterItem(
    character: Character,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .height(CHARACTER_ITEM_HEIGHT)
            .clickable { navController.navigate(Screen.Details.passCharacterId(character.id)) },
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(shape = RoundedCornerShape(size = LARGE_PADDING)) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data = character.image)
                    .placeholder(drawableResId = R.drawable.ic_error)
                    .error(drawableResId = R.drawable.ic_error)
                    .build(),
                contentDescription = stringResource(id = R.string.character_image),
                contentScale = ContentScale.Crop
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.2f)
                .fillMaxWidth(),
            color = Blue200,
            shape = RoundedCornerShape(
                bottomStart = LARGE_PADDING,
                bottomEnd = LARGE_PADDING
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MEDIUM_PADDING)
            ) {
                Text(
                    text = character.name,
                    color = MaterialTheme.colors.topAppBarContentColor,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
@Preview
fun CharacterItemPreview() {
    CharacterItem(
        character = Character(
            id = 1,
            name = "Rick Sanchez",
            image = "",
            status = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ",
            species = "",
            type = "",
            gender = ""
        ),
        navController = rememberNavController()
    )
}

@ExperimentalCoilApi
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun CharacterItemDarkPreview() {
    CharacterItem(
        character = Character(
            id = 1,
            name = "Rick Sanchez",
            image = "",
            status = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ",
            species = "",
            type = "",
            gender = ""
        ),
        navController = rememberNavController()
    )
}
