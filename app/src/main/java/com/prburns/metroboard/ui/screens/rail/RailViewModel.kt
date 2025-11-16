package com.prburns.metroboard.ui.screens.rail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prburns.metroboard.data.database.StationEntity
import com.prburns.metroboard.data.models.ElevatorIncident
import com.prburns.metroboard.data.models.RailIncident
import com.prburns.metroboard.data.models.Train
import com.prburns.metroboard.data.repository.RailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RailUiState(
    val favoriteStations: List<StationEntity> = emptyList(),
    val predictions: Map<String, List<Train>> = emptyMap(),
    val railIncidents: List<RailIncident> = emptyList(),
    val elevatorIncidents: List<ElevatorIncident> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class RailViewModel @Inject constructor(
    private val repository: RailRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RailUiState())
    val uiState: StateFlow<RailUiState> = _uiState.asStateFlow()

    init {
        observeFavoriteStations()
        refreshData()
    }

    private fun observeFavoriteStations() {
        viewModelScope.launch {
            repository.getFavoriteStations().collect { stations ->
                _uiState.update { it.copy(favoriteStations = stations) }
                if (stations.isNotEmpty()) {
                    fetchPredictions(stations)
                }
            }
        }
    }

    fun refreshData() {
        fetchIncidents()
        _uiState.value.favoriteStations.takeIf { it.isNotEmpty() }?.let {
            fetchPredictions(it)
        }
    }

    private fun fetchPredictions(stations: List<StationEntity>) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val stationCodes = stations.flatMap { it.stationIds }.joinToString(",")

            repository.getTrainPredictions(stationCodes)
                .onSuccess { response ->
                    val predictionMap = response.trains.groupBy { it.locationCode }
                    _uiState.update {
                        it.copy(
                            predictions = predictionMap,
                            isLoading = false,
                            error = null
                        )
                    }
                }
                .onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Failed to load predictions"
                        )
                    }
                }
        }
    }

    private fun fetchIncidents() {
        viewModelScope.launch {
            repository.getRailIncidents()
                .onSuccess { response ->
                    _uiState.update { it.copy(railIncidents = response.incidents) }
                }

            repository.getElevatorIncidents()
                .onSuccess { response ->
                    _uiState.update { it.copy(elevatorIncidents = response.elevatorIncidents) }
                }
        }
    }

    fun addFavoriteStation(station: StationEntity) {
        viewModelScope.launch {
            repository.addFavoriteStation(station)
        }
    }

    fun removeFavoriteStation(station: StationEntity) {
        viewModelScope.launch {
            repository.removeFavoriteStation(station)
        }
    }
}
