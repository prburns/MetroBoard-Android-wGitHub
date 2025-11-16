package com.prburns.metroboard.ui.screens.bus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prburns.metroboard.data.database.StopEntity
import com.prburns.metroboard.data.models.BusIncident
import com.prburns.metroboard.data.models.BusPrediction
import com.prburns.metroboard.data.repository.BusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BusUiState(
    val favoriteStops: List<StopEntity> = emptyList(),
    val predictions: Map<String, List<BusPrediction>> = emptyMap(),
    val busIncidents: List<BusIncident> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class BusViewModel @Inject constructor(
    private val repository: BusRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BusUiState())
    val uiState: StateFlow<BusUiState> = _uiState.asStateFlow()

    init {
        observeFavoriteStops()
        refreshData()
    }

    private fun observeFavoriteStops() {
        viewModelScope.launch {
            repository.getFavoriteStops().collect { stops ->
                _uiState.update { it.copy(favoriteStops = stops) }
                if (stops.isNotEmpty()) {
                    fetchPredictions(stops)
                }
            }
        }
    }

    fun refreshData() {
        fetchIncidents()
        _uiState.value.favoriteStops.takeIf { it.isNotEmpty() }?.let {
            fetchPredictions(it)
        }
    }

    private fun fetchPredictions(stops: List<StopEntity>) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            val predictionMap = mutableMapOf<String, List<BusPrediction>>()

            stops.forEach { stop ->
                repository.getBusPredictions(stop.stopId)
                    .onSuccess { response ->
                        predictionMap[stop.stopId] = response.predictions
                    }
            }

            _uiState.update {
                it.copy(
                    predictions = predictionMap,
                    isLoading = false,
                    error = null
                )
            }
        }
    }

    private fun fetchIncidents() {
        viewModelScope.launch {
            repository.getBusIncidents()
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(busIncidents = response.busIncidents ?: emptyList())
                    }
                }
        }
    }

    fun addFavoriteStop(stop: StopEntity) {
        viewModelScope.launch {
            repository.addFavoriteStop(stop)
        }
    }

    fun removeFavoriteStop(stop: StopEntity) {
        viewModelScope.launch {
            repository.removeFavoriteStop(stop)
        }
    }
}
