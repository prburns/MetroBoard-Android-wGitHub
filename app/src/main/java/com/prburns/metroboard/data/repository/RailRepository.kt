package com.prburns.metroboard.data.repository

import com.prburns.metroboard.data.database.StationDao
import com.prburns.metroboard.data.database.StationEntity
import com.prburns.metroboard.data.models.*
import com.prburns.metroboard.data.network.WMATAApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RailRepository @Inject constructor(
    private val apiService: WMATAApiService,
    private val stationDao: StationDao
) {
    suspend fun getTrainPredictions(stationCodes: String): Result<TrainPredictionsResponse> {
        return try {
            Result.success(apiService.getTrainPredictions(stationCodes))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getStations(): Result<WMATAStationResponse> {
        return try {
            Result.success(apiService.getStations())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getRailIncidents(): Result<RailIncidentsResponse> {
        return try {
            Result.success(apiService.getRailIncidents())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getElevatorIncidents(): Result<ElevatorIncidentsResponse> {
        return try {
            Result.success(apiService.getElevatorIncidents())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getFavoriteStations(): Flow<List<StationEntity>> {
        return stationDao.getAllStations()
    }

    suspend fun addFavoriteStation(station: StationEntity) {
        stationDao.insertStation(station)
    }

    suspend fun removeFavoriteStation(station: StationEntity) {
        stationDao.deleteStation(station)
    }

    suspend fun updateStation(station: StationEntity) {
        stationDao.updateStation(station)
    }
}
