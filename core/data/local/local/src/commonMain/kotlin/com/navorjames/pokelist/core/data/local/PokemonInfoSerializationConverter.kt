package com.navorjames.pokelist.core.data.local

import androidx.room.TypeConverter
import com.navorjames.pokelist.core.data.network.data.Ability
import com.navorjames.pokelist.core.data.network.data.AbilityInfo
import com.navorjames.pokelist.core.data.network.data.MoveInfo
import com.navorjames.pokelist.core.data.network.data.Moves
import com.navorjames.pokelist.core.data.network.data.Sprites
import kotlinx.serialization.json.Json

class PokemonInfoSerializationConverter {
    @TypeConverter
    fun abilitiesListToString(list: List<Ability>): String = list.encode()

    @TypeConverter
    fun stringToAbilitiesList(data: String): List<Ability> = data.decode()

    @TypeConverter
    fun abilitiesToString(ability: Ability): String = ability.encode()

    @TypeConverter
    fun stringToAbilities(data: String): Ability = data.decode()

    @TypeConverter
    fun abilityInfoToString(info: AbilityInfo): String = info.encode()

    @TypeConverter
    fun stringToAbilityInfo(data: String): AbilityInfo = data.decode()

    @TypeConverter
    fun movesListToString(list: List<Moves>): String = list.encode()

    @TypeConverter
    fun stringToMovesList(data: String): List<Moves> = data.decode()

    @TypeConverter
    fun movesToString(moves: Moves): String = moves.encode()

    @TypeConverter
    fun stringToMoves(data: String): Moves = data.decode()

    @TypeConverter
    fun moveInfoToString(move: MoveInfo): String = move.encode()

    @TypeConverter
    fun stringToMoveInfo(data: String): MoveInfo = data.decode()

    @TypeConverter
    fun spritesToString(sprites: Sprites): String = sprites.encode()

    @TypeConverter
    fun stringToSprites(data: String): Sprites = data.decode()

    private inline fun <reified T: Any> T.encode(): String = Json.encodeToString(this)
    private inline fun <reified T: Any> String.decode(): T = Json.decodeFromString(this)
}