package com.prburns.metroboard.ui.screens.alerts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prburns.metroboard.data.models.BusIncident
import com.prburns.metroboard.data.models.ElevatorIncident
import com.prburns.metroboard.data.models.RailIncident
import com.prburns.metroboard.data.repository.BusRepository
import com.prburns.metroboard.data.repository.RailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AlertsUiState(
    val railIncidents: List<RailIncident> = emptyList(),
    val elevatorIncidents: List<ElevatorIncident> = emptyList(),
    val busIncidents: List<BusIncident> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AlertsViewModel @Inject constructor(
    private val railRepository: RailRepository,
    private val busRepository: BusRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AlertsUiState())
    val uiState: StateFlow<AlertsUiState> = _uiState.asStateFlow()

    init {
        refreshAlerts()
    }

    fun refreshAlerts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            railRepository.getRailIncidents()
                .onSuccess { response ->
                    _uiState.update { it.copy(railIncidents = response.incidents) }
                }
                .onFailure { exception ->
                    _uiState.update { it.copy(error = exception.message) }
                }

            railRepository.getElevatorIncidents()
                .onSuccess { response ->
                    _uiState.update { it.copy(elevatorIncidents = response.elevatorIncidents) }
                }

            busRepository.getBusIncidents()
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(busIncidents = response.busIncidents ?: emptyList())
                    }
                }

            _uiState.update { it.copy(isLoading = false) }
        }
    }
}
