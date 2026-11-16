package com.example.pokedex.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.PokemonCardUiModel
import com.example.pokedex.repository.PokemonRepository
import kotlinx.coroutines.launch

sealed interface PokeUiState {
    data object Loading : PokeUiState
    data class Success(val cards: List<PokemonCardUiModel>) : PokeUiState
    data class Error(val message: String) : PokeUiState
}

class PokemonViewModel : ViewModel() {
    private val repository = PokemonRepository()

    val quickSuggestions = listOf("Charizard", "Pikachu", "Mewtwo", "Gengar", "Eevee")

    var searchQuery by mutableStateOf("Charizard")
    var uiState by mutableStateOf<PokeUiState>(PokeUiState.Loading)
    var selectedCard by mutableStateOf<PokemonCardUiModel?>(null)

    init {
        searchCards("Charizard")
    }

    fun searchCards(query: String) {
        if (query.isBlank()) return
        searchQuery = query
        uiState = PokeUiState.Loading
        viewModelScope.launch {
            try {
                val results = repository.searchCards(query.trim())
                uiState = PokeUiState.Success(results)
            } catch (e: Exception) {
                uiState = PokeUiState.Error(e.message ?: "Unknown Error")
            }
        }
    }

    fun selectCard(card: PokemonCardUiModel) {
        selectedCard = card
    }

    fun dismissCardDetail() {
        selectedCard = null
    }
}