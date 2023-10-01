package com.dfavilav.zararickmorty.presentation.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dfavilav.zararickmorty.domain.model.Character
import com.dfavilav.zararickmorty.domain.use_cases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery = _searchQuery

    private val _searchedCharacters = MutableStateFlow<PagingData<Character>>(PagingData.empty())
    val searchedCharacters = _searchedCharacters

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun searchCharacters(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.searchCharactersUseCase(query = query).cachedIn(viewModelScope).collect {
                _searchedCharacters.value = it
            }
        }
    }

}