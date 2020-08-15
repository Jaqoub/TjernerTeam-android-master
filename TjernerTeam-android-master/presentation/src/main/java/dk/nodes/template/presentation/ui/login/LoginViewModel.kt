package dk.nodes.template.presentation.ui.login

import dk.nodes.template.presentation.nstack.NStackPresenter
import dk.nodes.template.presentation.ui.base.BaseViewModel
import javax.inject.Inject

data class LoginViewModel @Inject constructor(
        private val nStackPresenter: NStackPresenter


): BaseViewModel<LoginViewState>() {
    override val initState: LoginViewState = LoginViewState()}


