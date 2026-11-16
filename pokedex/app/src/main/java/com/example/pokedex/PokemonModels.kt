package com.example.pokedex.model

import com.google.gson.annotations.SerializedName

data class PokemonSearchResponse(
    val data: List<PokemonCardDto>?
)

data class PokemonCardDto(
    val id: String?,
    val tcgPlayerId: String?,
    val name: String,
    val setName: String?,
    val setCode: String?,
    val cardNumber: String?,
    val rarity: String?,
    val cardType: String?,
    val hp: String?,
    val illustrator: String?,
    val artist: String?,
    val imageUrl: String?,
    val images: PokemonImagesDto?,
    val prices: PokemonPricesDto?
)

data class PokemonImagesDto(
    val small: String?,
    val large: String?
)

data class PokemonPricesDto(
    val market: Double?,
    val low: Double?,
    val mid: Double?,
    val high: Double?,
    val listings: Int?,
    val sellers: Int?,
    val primaryPrinting: String?,
    @SerializedName("tcgplayer")
    val tcgPlayer: TcgPlayerPriceDto?
)

data class TcgPlayerPriceDto(
    val marketPrice: Double?,
    val lowPrice: Double?,
    val activeListings: Int?,
    val verifiedSellers: Int?,
    val printing: String?
)

data class PokemonCardUiModel(
    val id: String,
    val name: String,
    val setName: String,
    val cardNumber: String,
    val hp: String,
    val rarity: String,
    val cardType: String,
    val illustrator: String,
    val imageUrl: String,
    val marketPrice: Double,
    val lowestPrice: Double,
    val activeListings: Int,
    val verifiedSellers: Int,
    val primaryPrinting: String
)