package dk.nodes.template.presentation.ui.shift

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dk.nodes.template.models.FacebookUser
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import dk.nodes.template.presentation.ui.main.MainActivityViewModel
import dk.nodes.template.presentation.ui.main.MainActivityViewState
import kotlinx.android.synthetic.main.activity_user_shifts.*
import timber.log.Timber

class UserShiftActivity : BaseActivity(),  BottomNavigationView.OnNavigationItemSelectedListener {

    private val viewModel by viewModel<MainActivityViewModel>()
    lateinit var activeShiftsFragment: Fragment
    lateinit var inactiveShiftsFragment: Fragment
    private var shownMenu: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shift_details)

        val bundle: Bundle? = intent.extras

        if (bundle?.getParcelable<FacebookUser?>("user") != null) {

            val user: FacebookUser? = (bundle.getParcelable<FacebookUser?>("user"))
            user?.let { viewModel.saveUser(it) }
        }

        activeShiftsFragment = ActiveShiftsFragment.newInstance()

        viewModel.viewState.observeNonNull(this) { state ->
            handleErrors(state)
        }

        top_user_shiftoverview_menu.setOnNavigationItemSelectedListener(this)
        inactiveShiftsFragment = InactiveShiftsFragment.newInstance()

        supportFragmentManager.beginTransaction()
                .add(R.id.user_shift_frame, activeShiftsFragment, "1")
                .add(R.id.user_shift_frame,inactiveShiftsFragment,"2")
                .show(activeShiftsFragment)
                .hide(inactiveShiftsFragment).commit()

    }

    override fun onNavigationItemSelected(item: MenuItem) : Boolean {

        if(shownMenu == item.itemId) return false
        when (item.itemId) {
            R.id.navigation_active_shifts -> {
                supportFragmentManager.beginTransaction()
                        .show(activeShiftsFragment)
                        .hide(inactiveShiftsFragment).commit()
            }


            R.id.navigation_inactive_shifts-> {
                supportFragmentManager.beginTransaction()
                        .hide(activeShiftsFragment)
                        .show(inactiveShiftsFragment).commit()
            }
        }
        shownMenu = item.itemId


        return true
    }

    private fun handleErrors(state: MainActivityViewState) {
        state.viewError?.let {
        }
    }
}
