package dk.nodes.template.presentation.ui.shift

import androidx.lifecycle.viewModelScope
import dk.nodes.template.domain.interactors.*
import dk.nodes.template.models.Shift
import dk.nodes.template.presentation.extensions.asResult
import dk.nodes.template.presentation.nstack.NStackPresenter
import dk.nodes.template.presentation.ui.base.BaseViewModel
import dk.nodes.template.presentation.util.SingleEvent
import dk.nodes.template.presentation.util.ViewErrorController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class ShiftViewModel @Inject constructor(
private val nStackPresenter: NStackPresenter,
private val fetchShiftsInteractor: FetchShiftsInteractor,
private val fetchActiveShiftsInteractor: FetchActiveShiftsInteractor,
private val fetchInactiveShiftsInteractor: FetchInactiveShiftsInteractor





): BaseViewModel<ShiftViewState>(){
    override val initState: ShiftViewState = ShiftViewState()


}

