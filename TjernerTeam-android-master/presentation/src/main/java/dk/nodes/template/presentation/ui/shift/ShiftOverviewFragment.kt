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
import kotlinx.android.synthetic.main.fragment_shift_overview.*

class ShiftOverviewFragment : BaseFragment() {
private val viewModel by viewModel<MainActivityViewModel>()

    private var mainContext: Context? = null
    private var adapter: ShiftOverviewAdapter? = null
    lateinit var shiftDetailsActivityIntent: Intent



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = mainContext?.let { ShiftOverviewAdapter(it, R.layout.shift_recyclerview_row) }
        refreshShifts()
        addItemOnclick()

        viewModel.viewState.observeNonNull(this){
            state->
            handleShift(state)
        }

        val swipeLayout = swiperefresh as SwipeRefreshLayout
        swipeLayout.setOnRefreshListener {
            refreshShifts()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       return inflater.inflate(R.layout.fragment_shift_overview, container, false)
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
                ShiftOverviewFragment().apply {

                }
    }


    private fun refreshShifts() {
        viewModel.fetchShifts()
        if (swiperefresh.isRefreshing) {
            swiperefresh.isRefreshing = false
        }
    }

    private fun handleShift(state: MainActivityViewState) {
        state.let {

            state.shiftOverviewList?.let { shiftOverview -> adapter?.addShifts(shiftOverview) }
            adapter?.notifyDataSetChanged()
            updateRecyclerView()
        }
    }

    fun updateRecyclerView(){
        rv_shift_overview.layoutManager = LinearLayoutManager(mainContext,LinearLayoutManager.VERTICAL,false)
        rv_shift_overview.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    private fun addItemOnclick() {
        adapter?.onItemClickedListener = {shift ->
            shiftDetailsActivityIntent = Intent(mainContext, ShiftDetailsActivity::class.java)
            shiftDetailsActivityIntent.putExtra("shift", shift)
            startActivity(shiftDetailsActivityIntent)
        }
    }


}
