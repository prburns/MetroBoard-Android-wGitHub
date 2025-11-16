package com.prburns.metroboard.data.models

import com.google.gson.annotations.SerializedName

data class TrainPredictionsResponse(
    @SerializedName("Trains")
    val trains: List<Train>
)

data class Train(
    @SerializedName("Car")
    val car: String?,
    @SerializedName("Destination")
    val destination: String,
    @SerializedName("DestinationCode")
    val destinationCode: String?,
    @SerializedName("DestinationName")
    val destinationName: String,
    @SerializedName("Group")
    val group: String,
    @SerializedName("Line")
    val line: String,
    @SerializedName("LocationCode")
    val locationCode: String,
    @SerializedName("LocationName")
    val locationName: String,
    @SerializedName("Min")
    val min: String?
) {
    val arrivalDisplay: String
        get() = when (min) {
            "ARR", "BRD" -> min
            null -> "---"
            else -> min
        }
}

data class WMATAStationResponse(
    @SerializedName("Stations")
    val stations: List<WMATAStation>
)

data class WMATAStation(
    @SerializedName("Name")
    val name: String,
    @SerializedName("Code")
    val code: String,
    @SerializedName("LineCode1")
    val lineCode1: String?,
    @SerializedName("LineCode2")
    val lineCode2: String?,
    @SerializedName("LineCode3")
    val lineCode3: String?,
    @SerializedName("LineCode4")
    val lineCode4: String?,
    @SerializedName("StationTogether1")
    val stationTogether1: String?,
    @SerializedName("StationTogether2")
    val stationTogether2: String?
) {
    val lines: List<String>
        get() = listOfNotNull(lineCode1, lineCode2, lineCode3, lineCode4)
}
