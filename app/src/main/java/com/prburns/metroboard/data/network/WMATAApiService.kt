package com.prburns.metroboard.data.network

import com.prburns.metroboard.data.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WMATAApiService {

    @GET("StationPrediction.svc/json/GetPrediction/{stationCodes}")
    suspend fun getTrainPredictions(
        @Path("stationCodes") stationCodes: String
    ): TrainPredictionsResponse

    @GET("Rail.svc/json/jStations")
    suspend fun getStations(): WMATAStationResponse

    @GET("Incidents.svc/json/Incidents")
    suspend fun getRailIncidents(): RailIncidentsResponse

    @GET("Incidents.svc/json/ElevatorIncidents")
    suspend fun getElevatorIncidents(): ElevatorIncidentsResponse

    @GET("NextBusService.svc/json/jPredictions")
    suspend fun getBusPredictions(
        @Query("StopID") stopId: String
    ): BusPredictions

    @GET("Incidents.svc/json/BusIncidents")
    suspend fun getBusIncidents(): BusIncidentResponse

    companion object {
        const val BASE_URL = "https://api.wmata.com/"
        const val API_KEY = "f29c6a5568bc412cbdbe9561773e360e"
    }
}
