package com.example.pokelist.viewmodels.repositories.poke_rooms

import androidx.room.TypeConverter
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Ability
import com.example.pokelist.viewmodels.repositories.poke_api.entities.AbilityInfo
import com.example.pokelist.viewmodels.repositories.poke_api.entities.MoveInfo
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Moves
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.pokelist.viewmodels.repositories.poke_api.entities.Sprites
import kotlinx.serialization.json.Json

class PokemonInfoGsonConverter {
    val gson = Gson()

    @TypeConverter
    fun abilitiesListToString(list: List<Ability>): String{
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToAbilitiesList(data: String): List<Ability>{
        val type = object: TypeToken<List<Ability>>(){}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun abilitiesToString(ability: Ability): String{
        return gson.toJson(ability)
    }

    @TypeConverter
    fun stringToAbilities(data: String): Ability{
        val type = object: TypeToken<Ability>(){}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun abilityInfoToString(info: AbilityInfo): String{
        return gson.toJson(info)
    }

    @TypeConverter
    fun stringToAbilityInfo(data: String): AbilityInfo{
        val type = object: TypeToken<AbilityInfo>(){}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun movesListToString(list: List<Moves>): String{
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToMovesList(data: String): List<Moves>{
        val type = object: TypeToken<List<Moves>>(){}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun movesToString(moves: Moves): String{
        return gson.toJson(moves)
    }

    @TypeConverter
    fun stringToMoves(data: String): Moves{
        val type = object: TypeToken<Moves>(){}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun moveInfoToString(move: MoveInfo): String{
        return gson.toJson(move)
    }

    @TypeConverter
    fun stringToMoveInfo(data: String): MoveInfo{
        val type = object: TypeToken<MoveInfo>(){}.type
        return gson.fromJson(data, type)
    }

    @TypeConverter
    fun spritesToString(sprites: Sprites): String{
        return gson.toJson(sprites)
    }

    @TypeConverter
    fun stringToSprites(data: String): Sprites {
        val type = object: TypeToken<Sprites>(){}.type
        return gson.fromJson(data, type)
    }
}