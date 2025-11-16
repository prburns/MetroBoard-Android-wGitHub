package com.prburns.metroboard.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [StationEntity::class, StopEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MetroBoardDatabase : RoomDatabase() {
    abstract fun stationDao(): StationDao
    abstract fun stopDao(): StopDao
}
