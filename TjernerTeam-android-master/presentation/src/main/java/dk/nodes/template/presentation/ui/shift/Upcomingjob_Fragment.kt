package dk.nodes.template.presentation.ui.shift

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseFragment
import dk.nodes.template.presentation.ui.main.MainActivityViewModel
import dk.nodes.template.presentation.ui.main.MainActivityViewState
import kotlinx.android.synthetic.main.fragment_upcomingjob.*

class Upcomingjob_Fragment : BaseFragment() {

    private var listener: ActiveShiftsFragment.OnFragmentInteractionListener? = null
    private var adapter: ShiftOverviewAdapter? = null
    private var mainContext: Context? = null
    private val viewModel by viewModel<MainActivityViewModel>()




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = mainContext?.let { ShiftOverviewAdapter(it, R.layout.shift_recyclerview_row) }
        refreshShifts()
        userEndShiftOnClick(view)
        viewModel.viewState.observeNonNull(this) {
            state ->
            handleShift(state)
        }

        val swipeLayout = swiperefresh_upcoming_job as SwipeRefreshLayout
        swipeLayout.setOnRefreshListener {
            refreshShifts()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_upcomingjob, container, false)

    }

    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainContext = context
    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)

    }

    companion object {
        @JvmStatic
        fun newInstance() =
                Upcomingjob_Fragment().apply {

                }
    }

    private fun refreshShifts() {
        viewModel.fetchActiveShifts()
        if (swiperefresh_upcoming_job.isRefreshing) {
            swiperefresh_upcoming_job.isRefreshing = false
        }
    }

    fun updateRecyclerView(){
        rv_upcoming_job.layoutManager = LinearLayoutManager(mainContext, LinearLayoutManager.VERTICAL,false)
        rv_upcoming_job.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    private fun handleShift(state: MainActivityViewState) {
        state.let {
            state.userActiveAssignShifts?.let { upcomingShiftOverview -> adapter?.addShifts(upcomingShiftOverview) }
            adapter?.notifyDataSetChanged()
            updateRecyclerView()
        }
    }

    private fun userEndShiftOnClick(v: View) {
        adapter?.onItemClickedListener = {

            val checkoutFragment = CheckoutFragment()
            checkoutFragment.showPopupWindow(v, viewModel, it)

        }
    }
}