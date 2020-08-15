package dk.nodes.template.presentation.ui.main

import androidx.lifecycle.viewModelScope
import com.facebook.AccessToken
import dk.nodes.template.domain.interactors.*
import dk.nodes.template.models.FacebookUser
import dk.nodes.template.models.Shift
import dk.nodes.template.models.SnoozeStatusAndDaysLeft
import dk.nodes.template.presentation.extensions.asResult
import dk.nodes.template.presentation.nstack.NStackPresenter
import dk.nodes.template.presentation.ui.base.BaseViewModel
import dk.nodes.template.presentation.util.SingleEvent
import dk.nodes.template.presentation.util.ViewErrorController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
        private val nStackPresenter: NStackPresenter,
        private val fetchShiftsInteractor: FetchShiftsInteractor,
        private val fetchFacebookUserInteractor: FetchFacebookUserInteractor,
        private val saveUserProfileInteractor: SaveUserProfileInteractor,
        private val saveUserInteractor: SaveUserInteractor,
        private val saveUserRequestedJobInteractor: SaveUserRequestedJobInteractor,
        private val cancelAssignedJobInteractor: SaveUserRequestedJobInteractor,
        private val saveUserSnoozeRequestInteractor: SaveUserSnoozeRequestInteractor,
        private val GetSnoozeStatusInteractor: GetSnoozeStatusInteractor,
        private val fetchActiveShiftsInteractor: FetchActiveShiftsInteractor,
        private val fetchInactiveShiftsInteractor: FetchInactiveShiftsInteractor,
        private val deleteUserInteractor: DeleteUserInteractor,
        private val saveUserCheckoutInteractor: SaveUserCheckoutInteractor
) : BaseViewModel<MainActivityViewState>() {
    override val initState: MainActivityViewState = MainActivityViewState()


    private fun fetchShiftResult(result: CompleteResult<ArrayList<Shift>>): MainActivityViewState {
        return when (result) {
            is Success -> state.copy(shiftOverviewList = result.data)
            is Loading<*> -> state.copy(isLoading = true)
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> (MainActivityViewState())
        }

    }

    private fun fetchActiveShiftsResult(result: CompleteResult<ArrayList<Shift>>): MainActivityViewState {
        return when (result) {
            is Success -> state.copy(userActiveAssignShifts = result.data)
            is Loading<*> -> state.copy(isLoading = true)
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> (MainActivityViewState())
        }

    }

    private fun fetchInactiveShiftsResult(result: CompleteResult<ArrayList<Shift>>): MainActivityViewState {
        return when (result) {
            is Success -> state.copy(userInactiveAssignShifts = result.data)
            is Loading<*> -> state.copy(isLoading = true)
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> (MainActivityViewState())
        }

    }

    fun fetchShifts() = viewModelScope.launch {
        val userId = AccessToken.getCurrentAccessToken().userId.toLong()
        val result = withContext(Dispatchers.IO) { fetchShiftsInteractor.asResult().invoke(userId) }
        state = fetchShiftResult(result)
    }

    fun fetchActiveShifts() = viewModelScope.launch {
        val userId = AccessToken.getCurrentAccessToken().userId.toLong()
        val result = withContext(Dispatchers.IO) { fetchActiveShiftsInteractor.asResult().invoke(userId) }
        state = fetchActiveShiftsResult(result)

    }

    fun fetchInactiveShifts() = viewModelScope.launch {
        val userId = AccessToken.getCurrentAccessToken().userId.toLong()
        val result = withContext(Dispatchers.IO) { fetchInactiveShiftsInteractor.asResult().invoke(userId) }
        state = fetchInactiveShiftsResult(result)

    }

    fun saveUser(facebookUser: FacebookUser) = viewModelScope.launch {

        val result = withContext(Dispatchers.IO) { saveUserInteractor.asResult().invoke(facebookUser) }
        state = saveFacebookUserResult(result)

    }

    fun updateUser(facebookUser: FacebookUser) = viewModelScope.launch {

        val result = withContext(Dispatchers.IO) { saveUserProfileInteractor.asResult().invoke(facebookUser) }
        state = saveFacebookUserResult(result)

    }

    private fun saveFacebookUserResult(result: CompleteResult<Unit>): MainActivityViewState {
        return when (result) {
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> (MainActivityViewState())
        }

    }

    fun fetchUser(facebookId: String) = viewModelScope.launch {

        val result = withContext(Dispatchers.IO) { fetchFacebookUserInteractor.asResult().invoke(facebookId) }
        state = fetchUsers(result)

    }


    private fun fetchUsers(result: CompleteResult<FacebookUser>): MainActivityViewState {
        return when (result) {

            is Success -> state.copy(facebookUser = result.data)
            is Loading<*> -> state.copy(isLoading = true)
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> (MainActivityViewState())

        }

    }

    fun saveUserCheckout(userId: String?, checkin: String, checkout: String, shiftId: String?) = viewModelScope.launch {
        val userCheckoutShift = arrayOf(userId, checkin, checkout, shiftId)
        withContext(Dispatchers.IO) { saveUserCheckoutInteractor.asResult().invoke(userCheckoutShift) }
    }


    fun saveUserRequestedJob(userId: Long, shiftId: Long?) = viewModelScope.launch {
        val userAndShiftIdArray = arrayOf(userId, shiftId)
        withContext(Dispatchers.IO) { saveUserRequestedJobInteractor.asResult().invoke(userAndShiftIdArray) }
    }

    fun cancelAssignedJob(userId: Long, shiftId: Long?) = viewModelScope.launch {
        val userAndShiftIdArray = arrayOf(userId, shiftId)
        withContext(Dispatchers.IO) { cancelAssignedJobInteractor.asResult().invoke(userAndShiftIdArray) }
    }


    fun deleteUser(facebookid: Long) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            deleteUserInteractor.asResult().invoke(facebookid)
        }
    }


    fun saveUserSnoozeRequest(userIdAndSnoozeValue: Array<String>) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            saveUserSnoozeRequestInteractor.asResult().invoke(userIdAndSnoozeValue)
        }
    }

    fun getSnoozeStatus(user: String) = viewModelScope.launch {
        val result = withContext(Dispatchers.IO) { GetSnoozeStatusInteractor.asResult().invoke(user) }

        state = getSnoozeStatusResult(result)
    }

    private fun getSnoozeStatusResult(result: CompleteResult<SnoozeStatusAndDaysLeft>): MainActivityViewState {

        return when (result) {
            is Success -> state.copy(snoozeStatusAndDaysLeft = result.data)
            is Loading<*> -> state.copy(isLoading = true)
            is Fail -> state.copy(
                    viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                    isLoading = false
            )
            else -> (MainActivityViewState())

        }

    }


}
