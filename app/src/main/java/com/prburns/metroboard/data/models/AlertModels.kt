package com.prburns.metroboard.data.models

import com.google.gson.annotations.SerializedName

data class RailIncidentsResponse(
    @SerializedName("Incidents")
    val incidents: List<RailIncident>
)

data class RailIncident(
    @SerializedName("IncidentID")
    val incidentId: String,
    @SerializedName("Description")
    val description: String,
    @SerializedName("LinesAffected")
    val linesAffected: String
) {
    val affectedLines: List<String>
        get() = linesAffected.split(",").map { it.trim() }.filter { it.isNotEmpty() }
}

data class ElevatorIncidentsResponse(
    @SerializedName("ElevatorIncidents")
    val elevatorIncidents: List<ElevatorIncident>
)

data class ElevatorIncident(
    @SerializedName("UnitName")
    val unitName: String,
    @SerializedName("UnitType")
    val unitType: String,
    @SerializedName("StationCode")
    val stationCode: String,
    @SerializedName("StationName")
    val stationName: String,
    @SerializedName("LocationDescription")
    val locationDescription: String?,
    @SerializedName("SymptomCode")
    val symptomCode: String?,
    @SerializedName("TimeOutOfService")
    val timeOutOfService: String?,
    @SerializedName("SymptomDescription")
    val symptomDescription: String?,
    @SerializedName("DisplayOrder")
    val displayOrder: Int?,
    @SerializedName("DateOutOfServ")
    val dateOutOfServ: String?,
    @SerializedName("DateUpdated")
    val dateUpdated: String?,
    @SerializedName("EstimatedReturnToService")
    val estimatedReturnToService: String?
) {
    val displayType: String
        get() = when (unitType.uppercase()) {
            "ELEVATOR" -> "Elevator"
            "ESCALATOR" -> "Escalator"
            else -> unitType
        }

    val displayDescription: String
        get() = locationDescription?.takeIf { it.isNotEmpty() }
            ?: symptomDescription
            ?: "Out of service"
}

data class BusIncidentResponse(
    @SerializedName("BusIncidents")
    val busIncidents: List<BusIncident>?
)

data class BusIncident(
    @SerializedName("DateUpdated")
    val dateUpdated: String?,
    @SerializedName("Description")
    val description: String?,
    @SerializedName("IncidentID")
    val incidentId: String?,
    @SerializedName("IncidentType")
    val incidentType: String?,
    @SerializedName("RoutesAffected")
    val routesAffected: List<String>?
) {
    val id: String
        get() = incidentId ?: "${dateUpdated}-${description?.take(20)}"
}
