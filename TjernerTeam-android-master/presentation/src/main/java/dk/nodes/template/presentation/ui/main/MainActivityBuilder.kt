package dk.nodes.template.presentation.ui.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import dk.nodes.template.presentation.injection.ViewModelKey
import dk.nodes.template.presentation.ui.options.UserOptionsFragment
import dk.nodes.template.presentation.ui.sample.SampleBuilder
import dk.nodes.template.presentation.ui.shift.*


@Module
internal abstract class MainActivityBuilder {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewMode(viewModel: MainActivityViewModel): ViewModel

    @ContributesAndroidInjector(
        modules = [
            SampleBuilder::class
        ]
    )
    abstract fun MainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun ShiftOverviewFragment(): ShiftOverviewFragment

    @ContributesAndroidInjector
    abstract fun shiftDetailsActivity(): ShiftDetailsActivity

    @ContributesAndroidInjector
    abstract fun UserOptionsFragment(): UserOptionsFragment

    @ContributesAndroidInjector
    abstract fun JobFragment(): JobFragment


    @ContributesAndroidInjector
    abstract fun ShiftEndFragment(): ShiftEndFragment

    @ContributesAndroidInjector
    abstract fun ShiftEndActivity(): UserShiftActivity

    @ContributesAndroidInjector
    abstract fun ActiveShiftsFragment(): ActiveShiftsFragment

   @ContributesAndroidInjector
    abstract fun EditUserActivity(): EditUserActivity

    @ContributesAndroidInjector
    abstract fun Upcomingjob_Fragment(): Upcomingjob_Fragment

    @ContributesAndroidInjector
    abstract fun Completed_job_fragment(): Completed_job_fragment


}
