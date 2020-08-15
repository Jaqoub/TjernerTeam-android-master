package dk.nodes.template.presentation.ui.login

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import dk.nodes.template.presentation.injection.ViewModelKey

@Module
internal abstract class LoginBuilder {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginActivity(viewModel: LoginViewModel): ViewModel

    @ContributesAndroidInjector
    internal abstract fun LoginAcktivity(): LoginActivity
}