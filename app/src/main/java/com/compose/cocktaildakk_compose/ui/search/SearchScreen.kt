@file:OptIn(ExperimentalFoundationApi::class)

package com.compose.cocktaildakk_compose.ui.search

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.SingletonObject.VISIBLE_SEARCH_STR
import com.compose.cocktaildakk_compose.ui.domain.model.ApplicationState
import com.compose.cocktaildakk_compose.ui.search.searchResult.ElasticSearchScreen
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot
import com.compose.cocktaildakk_compose.ui.utils.CustomTextField
import com.compose.cocktaildakk_compose.ui.utils.NoRippleTheme

@Composable
fun SearchScreen(
    appState: ApplicationState,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val focusManager = LocalFocusManager.current
    val focusRequest = remember {
        FocusRequester()
    }
    val searchCocktailList = searchViewModel.pagingCocktailList.collectAsLazyPagingItems()
    val textFieldValue = searchViewModel.textFieldValue.collectAsState()

    LaunchedEffect(Unit) {
        focusRequest.requestFocus()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color_Default_Backgounrd),
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                    contentDescription = "Icon Back Arrow",
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            focusManager.clearFocus()
                            appState.navController.popBackStack()
                        },
                )
            }

            CustomTextField(
                trailingIcon = {
                    if (textFieldValue.value.text.isNotEmpty()) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_close_24),
                            contentDescription = "Icon Close",
                            tint = Color_Default_Backgounrd,
                            modifier = Modifier.clickable {
                                searchViewModel.textFieldValue.value = TextFieldValue()
                            },
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(20.dp),
                    ),
                focusRequest = focusRequest,
                fontSize = 16.sp,
                value = textFieldValue.value,
                onvalueChanged = { searchViewModel.textFieldValue.value = it },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    val text = textFieldValue.value.text
                    searchViewModel.addSearchStr(text)
                    VISIBLE_SEARCH_STR.value = text
                    onSearch(text, focusManager, appState)
                }),
            )
        }

        if (textFieldValue.value.text.isEmpty()) {
            OnSearchNothing(searchViewModel, focusManager, appState)
        } else {
            ElasticSearchScreen(
                searchCocktailList = searchCocktailList,
                navController = appState.navController,
            )
        }
    }
}

fun onSearch(
    textFieldValue: String,
    focusManager: FocusManager,
    appState: ApplicationState,
) {
    focusManager.clearFocus()
    VISIBLE_SEARCH_STR.value = textFieldValue
    navigateToMainGraph(destination = ScreenRoot.SEARCH_RESULT, appState.navController)
}
