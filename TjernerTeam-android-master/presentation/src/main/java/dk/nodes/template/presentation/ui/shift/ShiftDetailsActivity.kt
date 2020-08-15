package dk.nodes.template.presentation.ui.shift

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dk.nodes.template.models.FacebookUser
import dk.nodes.template.models.Shift
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseActivity
import dk.nodes.template.presentation.ui.main.MainActivityViewModel
import dk.nodes.template.presentation.ui.main.MainActivityViewState
import kotlinx.android.synthetic.main.activity_shift_details.*

class ShiftDetailsActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private val viewModel by viewModel<MainActivityViewModel>()
    lateinit var jobFragment: Fragment
    lateinit var messageFragment: Fragment


    private var shownMenu: Int = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shift_details)


        val bundle: Bundle? = intent.extras

        if (bundle?.getParcelable<FacebookUser?>("user") != null) {

            val user: FacebookUser? = (bundle.getParcelable<FacebookUser?>("user"))
            user?.let { viewModel.saveUser(it) }
        }

        val shift = intent.getParcelableExtra<Shift>("shift")
        jobFragment = JobFragment.newInstance(shift)

        viewModel.viewState.observeNonNull(this) { state ->
            handleErrors(state)
        }

        top_shiftoverview_menu.setOnNavigationItemSelectedListener(this)
        messageFragment = MessageFragment.newInstance()
        supportFragmentManager.beginTransaction()
                .add(R.id.shift_frame, jobFragment, "1")
                .add(R.id.shift_frame,messageFragment,"2")
                .show(jobFragment)
                .hide(messageFragment)
                .commit()

    }

    override fun onNavigationItemSelected(item: MenuItem) : Boolean {

        if(shownMenu == item.itemId) return false
        when (item.itemId) {
            R.id.navigation_job_info -> {
                supportFragmentManager.beginTransaction()
                        .show(jobFragment)
                        .hide(messageFragment)
                        .commit()
            }


            R.id.navigation_message-> {
                supportFragmentManager.beginTransaction()
                        .hide(jobFragment)
                        .show(messageFragment)
                        .commit()
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

