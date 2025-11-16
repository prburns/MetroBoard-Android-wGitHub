package com.prburns.metroboard.data.repository

import com.prburns.metroboard.data.database.StopDao
import com.prburns.metroboard.data.database.StopEntity
import com.prburns.metroboard.data.models.BusIncidentResponse
import com.prburns.metroboard.data.models.BusPredictions
import com.prburns.metroboard.data.network.WMATAApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusRepository @Inject constructor(
    private val apiService: WMATAApiService,
    private val stopDao: StopDao
) {
    suspend fun getBusPredictions(stopId: String): Result<BusPredictions> {
        return try {
            Result.success(apiService.getBusPredictions(stopId))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBusIncidents(): Result<BusIncidentResponse> {
        return try {
            Result.success(apiService.getBusIncidents())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getFavoriteStops(): Flow<List<StopEntity>> {
        return stopDao.getAllStops()
    }

    suspend fun addFavoriteStop(stop: StopEntity) {
        stopDao.insertStop(stop)
    }

    suspend fun removeFavoriteStop(stop: StopEntity) {
        stopDao.deleteStop(stop)
    }

    suspend fun updateStop(stop: StopEntity) {
        stopDao.updateStop(stop)
    }
}
