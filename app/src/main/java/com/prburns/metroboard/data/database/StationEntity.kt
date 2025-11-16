package com.prburns.metroboard.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Entity(tableName = "stations")
data class StationEntity(
    @PrimaryKey
    val id: String,
    val stationIds: List<String>,
    val name: String,
    val lines: List<String>,
    val excludedDestinations: List<String> = emptyList()
)

@Entity(tableName = "stops")
data class StopEntity(
    @PrimaryKey
    val stopId: String,
    val name: String,
    val routes: List<String>,
    val selectedDestinations: List<String> = emptyList()
)

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}
