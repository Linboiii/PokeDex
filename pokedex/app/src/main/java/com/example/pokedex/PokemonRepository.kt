package com.example.pokedex.repository

import com.example.pokedex.model.PokemonCardDto
import com.example.pokedex.model.PokemonCardUiModel
import com.example.pokedex.service.PokemonService
import retrofit2.HttpException

class PokemonRepository {
    private val api = PokemonService.api

    suspend fun searchCards(query: String): List<PokemonCardUiModel> {
        try {
            val response = api.searchCards(query = query)
            val rawCards = response.data ?: emptyList()
            if (rawCards.isEmpty()) {
                throw Exception("No cards found for \"$query\"")
            }
            return rawCards.mapIndexed { index, dto -> dto.toUiModel(index) }
        } catch (e: HttpException) {
            throw Exception("HTTP ${e.code()}")
        }
    }

    private fun PokemonCardDto.toUiModel(index: Int): PokemonCardUiModel {
        val resolvedMarket = prices?.market
            ?: prices?.tcgPlayer?.marketPrice
            ?: 0.0
        val resolvedLow = prices?.low
            ?: prices?.tcgPlayer?.lowPrice
            ?: resolvedMarket
        val resolvedListings = prices?.listings
            ?: prices?.tcgPlayer?.activeListings
            ?: 0
        val resolvedSellers = prices?.sellers
            ?: prices?.tcgPlayer?.verifiedSellers
            ?: 0
        val resolvedPrinting = prices?.primaryPrinting
            ?: prices?.tcgPlayer?.printing
            ?: "Holofoil"
        val resolvedImage = images?.large
            ?: images?.small
            ?: imageUrl
            ?: ""

        return PokemonCardUiModel(
            id = id ?: tcgPlayerId ?: "$name-$index",
            name = name,
            setName = setName ?: "Unknown Set",
            cardNumber = cardNumber ?: "-",
            hp = hp ?: "",
            rarity = rarity ?: "Common",
            cardType = cardType ?: "Pokemon",
            illustrator = illustrator ?: artist ?: "Unknown",
            imageUrl = resolvedImage,
            marketPrice = resolvedMarket,
            lowestPrice = resolvedLow,
            activeListings = resolvedListings,
            verifiedSellers = resolvedSellers,
            primaryPrinting = resolvedPrinting
        )
    }
}