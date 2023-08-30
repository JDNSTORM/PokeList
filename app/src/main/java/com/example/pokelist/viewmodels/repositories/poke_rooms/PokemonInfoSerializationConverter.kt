package com.example.pokelist.viewmodels.repositories.poke_rooms

import androidx.room.TypeConverter
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Ability
import com.example.pokelist.viewmodels.repositories.poke_api.entities.AbilityInfo
import com.example.pokelist.viewmodels.repositories.poke_api.entities.MoveInfo
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Moves
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Sprites
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PokemonInfoSerializationConverter {
    @TypeConverter
    fun abilitiesListToString(list: List<Ability>): String{
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun stringToAbilitiesList(data: String): List<Ability>{
        return Json.decodeFromString(data)
    }

    @TypeConverter
    fun abilitiesToString(ability: Ability): String{
        return Json.encodeToString(ability)
    }

    @TypeConverter
    fun stringToAbilities(data: String): Ability{
        return Json.decodeFromString(data)
    }

    @TypeConverter
    fun abilityInfoToString(info: AbilityInfo): String{
        return Json.encodeToString(info)
    }

    @TypeConverter
    fun stringToAbilityInfo(data: String): AbilityInfo{
        return Json.decodeFromString(data)
    }

    @TypeConverter
    fun movesListToString(list: List<Moves>): String{
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun stringToMovesList(data: String): List<Moves>{
        return Json.decodeFromString(data)
    }

    @TypeConverter
    fun movesToString(moves: Moves): String{
        return Json.encodeToString(moves)
    }

    @TypeConverter
    fun stringToMoves(data: String): Moves{
        return Json.decodeFromString(data)
    }

    @TypeConverter
    fun moveInfoToString(move: MoveInfo): String{
        return Json.encodeToString(move)
    }

    @TypeConverter
    fun stringToMoveInfo(data: String): MoveInfo{
        return Json.decodeFromString(data)
    }

    @TypeConverter
    fun spritesToString(sprites: Sprites): String{
        return Json.encodeToString(sprites)
    }

    @TypeConverter
    fun stringToSprites(data: String): Sprites {
        return Json.decodeFromString(data)
    }
}