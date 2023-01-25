package com.example.pokekotlin

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "pokemon")
data class Pokemon(
    @PrimaryKey val id: Int,
    val location_area_encounters: String,
    val name: String,
    val weight: Int,
    val height: Int,
    @Embedded(prefix = "sprites_") val sprites: Sprites,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId"
    ) val stats: List<Stat>,
    val types: List<Type>,
)

data class Sprites(
    val front_default: String,
    val front_shiny: String
)

@Entity(tableName = "stats")
data class Stat(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val base_stat: Int,
    val stat: NamedApiResource,
    val pokemonId: Int
)

@Entity(tableName = "types")
data class Type(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val type: NamedApiResource,
    val pokemonId: Int
)

data class NamedApiResource(
    val name: String,
    val url: String
)
