package dk.nodes.template.presentation.ui.options

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import com.facebook.AccessToken
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.extensions.observeNonNull
import dk.nodes.template.presentation.ui.base.BaseFragment
import dk.nodes.template.presentation.ui.main.EditUserActivity
import dk.nodes.template.presentation.ui.main.MainActivityViewModel
import dk.nodes.template.presentation.ui.main.MainActivityViewState
import kotlinx.android.synthetic.main.fragment_user_options.*

class UserOptionsFragment : BaseFragment(), View.OnClickListener {
    private val viewModel by viewModel<MainActivityViewModel>()
    private var mainContext: Context? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.viewState.observeNonNull(this) { state ->
            handleStatus(state)

        }

        chat_btn.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(mainContext, Uri.parse("https://m.facebook.com/thomas.tjenerteamet"))
        }

        faq_btn.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(mainContext, Uri.parse("http://www.tjenerteamet.dk/faq-app.html"))
        }

        snooze_btn.setOnClickListener(this)
        profile_btn.setOnClickListener(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_options, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainContext = context
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                UserOptionsFragment().apply {
                }
    }

    fun isLoggedIn(): String {
        val accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken != null) {
            return accessToken.userId
        }
        return "-1"
    }


    fun handleStatus(state: MainActivityViewState?) {

        state?.snoozeStatusAndDaysLeft?.let { snoozeobj ->
            val isSnoozed = snoozeobj.userIsSnoozed

            if (isSnoozed != null) {

                if (isSnoozed) {
                    val cancelSnoozePopup = CancelSnoozeNotificationPopUpFragment()
                    if(snoozeobj.snoozeDate ==null){
                        snoozeobj.snoozeDate = "Sluk"
                    }
                    view?.let { cancelSnoozePopup.showPopupWindow(it, viewModel, snoozeobj.snoozeDate!!) }
                } else {
                    val snoozePopup = SnoozeNotificationPopUpFragment()
                    view?.let { snoozePopup.showPopupWindow(it, viewModel) }
                }


            }
        }
    }

    override fun onClick(v: View) {

        when(v.id){

            snooze_btn.id-> {
                viewModel.getSnoozeStatus(isLoggedIn())


            }

            profile_btn.id ->{
                startActivity(Intent(mainContext, EditUserActivity::class.java))

            }



        }



    }


}