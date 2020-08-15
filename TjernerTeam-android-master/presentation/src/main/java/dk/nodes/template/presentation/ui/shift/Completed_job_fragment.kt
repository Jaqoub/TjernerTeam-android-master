package dk.nodes.template.presentation.ui.shift

import android.content.Context
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
import kotlinx.android.synthetic.main.fragment_completed_job.*

class Completed_job_fragment : BaseFragment() {

    private var listener: InactiveShiftsFragment.OnFragmentInteractionListener? = null
    private var adapter: ShiftOverviewAdapter? = null
    private var mainContext: Context? = null
    private val viewModel by viewModel<MainActivityViewModel>()




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = mainContext?.let { ShiftOverviewAdapter(it, R.layout.shift_recyclerview_row) }
        refreshShifts()

        viewModel.viewState.observeNonNull(this) { state ->
            handleShift(state)
        }

        val swipeLayout = swiperefresh_completed_jobs as SwipeRefreshLayout
        swipeLayout.setOnRefreshListener {
            refreshShifts()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_completed_job, container, false)

    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainContext = context
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                Completed_job_fragment().apply {

                }
    }

    private fun refreshShifts() {
        viewModel.fetchInactiveShifts()
        if (swiperefresh_completed_jobs.isRefreshing) {
            swiperefresh_completed_jobs.isRefreshing = false
        }
    }

    fun updateRecyclerView(){
        rv_completed_jobs.layoutManager = LinearLayoutManager(mainContext, LinearLayoutManager.VERTICAL,false)
        rv_completed_jobs.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    private fun handleShift(state: MainActivityViewState) {
        state.let {
            state.userInactiveAssignShifts?.let { completedShiftOverview -> adapter?.addShifts(completedShiftOverview) }
            adapter?.notifyDataSetChanged()
            updateRecyclerView()
        }
    }
}