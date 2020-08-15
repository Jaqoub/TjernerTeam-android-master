package dk.nodes.template.presentation.ui.options

import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.facebook.AccessToken
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.ui.main.MainActivityViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class CancelSnoozeNotificationPopUpFragment {
    fun showPopupWindow(view: View, viewModel: MainActivityViewModel, snoozeDaysLeft: String){
        val inflater: LayoutInflater = LayoutInflater.from(view.context)
        val popupView = inflater.inflate(R.layout.popup_cancel_snooze, null)
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT

        val popupWindow = PopupWindow(popupView, width, height, true)

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        val cancelCurrentSnoozeBtn = popupView.findViewById(R.id.snooze_cancel_current_snooze_btn) as Button
        val snoozeDaysAmountLeftText = popupView.findViewById(R.id.snooze_amount_day_left_txt) as TextView
        val cancelSnoozeAnnulmentBtn = popupView.findViewById(R.id.cancel_snooze_annulment_btn) as Button

        if (snoozeDaysLeft == "Sluk") {
            snoozeDaysAmountLeftText.text = view.context.getString(R.string.notifikationer_slukket)
        }else {


            val dateFormatter= DateTimeFormatter.ofPattern("dd/MM/YYYY")


            snoozeDaysAmountLeftText.text = "Notificationer er slukket til d." + dateFormatter.format(LocalDate.parse(snoozeDaysLeft))
        }

        cancelCurrentSnoozeBtn.setOnClickListener {
            viewModel.saveUserSnoozeRequest(arrayOf(isLoggedIn(), "0"))
            popupWindow.dismiss()
        }

        cancelSnoozeAnnulmentBtn.setOnClickListener {
            popupWindow.dismiss()
        }

        popupView.setOnTouchListener { v: View, m: MotionEvent ->
           // popupWindow.dismiss()
            true
        }
    }

    fun isLoggedIn(): String {
        val accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken != null) {
            return accessToken.userId
        }
        return "-1"
    }



}