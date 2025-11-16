package com.prburns.metroboard.data.models

import com.google.gson.annotations.SerializedName

data class BusPredictions(
    @SerializedName("Predictions")
    val predictions: List<BusPrediction>
)

data class BusPrediction(
    @SerializedName("DirectionText")
    val directionText: String,
    @SerializedName("RouteID")
    val routeId: String,
    @SerializedName("Minutes")
    val minutes: Int,
    @SerializedName("VehicleID")
    val vehicleId: String
)
