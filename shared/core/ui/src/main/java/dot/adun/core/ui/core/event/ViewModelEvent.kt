package dot.adun.core.ui.core.event

sealed interface ViewModelEvent : ViewEvent {
    data object NavigateBack : ViewModelEvent
}
