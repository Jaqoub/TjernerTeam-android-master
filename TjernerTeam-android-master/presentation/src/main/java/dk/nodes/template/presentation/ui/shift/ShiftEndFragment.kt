package dk.nodes.template.presentation.ui.shift

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dk.nodes.template.models.Shift
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseFragment
import dk.nodes.template.presentation.ui.main.MainActivityViewModel
import kotlinx.android.synthetic.main.fragment_active_shifts.*
import kotlinx.android.synthetic.main.fragment_job.*
import kotlinx.android.synthetic.main.fragment_shift_end.*
private const val shiftArg = "shift"


class ShiftEndFragment : BaseFragment(), BottomNavigationView.OnNavigationItemSelectedListener {


    private val viewModel by viewModel<MainActivityViewModel>()
    private var mainContext: Context? = null
    private var adapter: UserShiftAdapter? = null
    lateinit var UserShiftActivityIntent: Intent
    lateinit var inactiveShiftsFragment : Fragment
    lateinit var upcomingjobFragment: Fragment
    private var menutab: Int = 0
    var shift: Shift? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inactiveShiftsFragment = Completed_job_fragment.newInstance()
        upcomingjobFragment = Upcomingjob_Fragment.newInstance()
        bottomnavigation_main.setOnNavigationItemSelectedListener(this)


        childFragmentManager.beginTransaction()
                .add(R.id.shifts_container, inactiveShiftsFragment, "1")
                .add(R.id.shifts_container, upcomingjobFragment, "2")
                .show(upcomingjobFragment)
                .hide(inactiveShiftsFragment)
                .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (menutab == item.itemId) return false
        when (item.itemId) {
            R.id.navigation_active_shifts -> {
                childFragmentManager.beginTransaction()
                        .show(upcomingjobFragment)
                        .hide(inactiveShiftsFragment)
                        .commit()
            }
            R.id.navigation_inactive_shifts -> {
                childFragmentManager.beginTransaction()
                        .show(inactiveShiftsFragment)
                        .hide(upcomingjobFragment)
                        .commit()
            }
        }
            menutab = item.itemId


        return true

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shift_end, container, false)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            shift = it.getParcelable(shiftArg)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainContext = context
    }




    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }


    private fun addItemOnclick() {
        adapter?.onItemClickedListener = {shift ->
            UserShiftActivityIntent = Intent(mainContext, UserShiftActivity::class.java)
            UserShiftActivityIntent.putExtra("shift", shift)
            startActivity(UserShiftActivityIntent)
        }
    }




    companion object {
        @JvmStatic
        fun newInstance() =
                ShiftEndFragment().apply {

                }
    }



}
