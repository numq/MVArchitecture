package com.numq.mvarchitecture.presentation.feature.mvi.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class Feature<State : Feature.State, Event : Feature.Event, Effect : Feature.Effect> constructor(
    initialState: State
) : ViewModel() {

    interface State
    interface Event
    interface Effect

    abstract fun handleEvent(event: Event)

    private val event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    val effect: MutableSharedFlow<Effect> = MutableSharedFlow()

    val reduce: ((State) -> (State)) -> Unit by lazy {
        { newState ->
            _state.update {
                newState(it)
            }
        }
    }

    fun dispatch(e: Event) {
        viewModelScope.launch { event.emit(e) }
    }

    fun reveal(e: Effect) {
        viewModelScope.launch { effect.emit(e) }
    }

    init {
        viewModelScope.launch {
            event.collectLatest {
                handleEvent(it)
            }
        }
    }
}
