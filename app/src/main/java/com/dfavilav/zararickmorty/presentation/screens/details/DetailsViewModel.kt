package com.dfavilav.zararickmorty.presentation.screens.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dfavilav.zararickmorty.domain.model.Character
import com.dfavilav.zararickmorty.domain.use_cases.UseCases
import com.dfavilav.zararickmorty.util.Constants.DETAILS_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _selectedCharacter: MutableStateFlow<Character?> = MutableStateFlow(null)
    val selectedCharacter: StateFlow<Character?> = _selectedCharacter

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val characterId = savedStateHandle.get<Int>(DETAILS_ARGUMENT_KEY)
            _selectedCharacter.value = characterId?.let { useCases.getSelectedCharacterUseCase(characterId) }
        }
    }

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent.asSharedFlow()

    private val _colorPalette: MutableState<Map<String, String>> = mutableStateOf(mapOf())
    val colorPalette: State<Map<String, String>> = _colorPalette

    fun generateColorPalette() {
        viewModelScope.launch {
            _uiEvent.emit(UiEvent.GenerateColorPalette)
        }
    }

    fun setColorPalette(colors: Map<String, String>) {
        _colorPalette.value = colors
    }

}

sealed class UiEvent {
    data object GenerateColorPalette : UiEvent()
}
