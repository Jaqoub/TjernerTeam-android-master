package dk.nodes.template.presentation.ui.options

import android.view.*
import dk.nodes.template.presentation.R
import android.widget.*
import com.facebook.AccessToken
import dk.nodes.template.presentation.ui.base.BaseFragment
import dk.nodes.template.presentation.ui.main.MainActivityViewModel

class SnoozeNotificationPopUpFragment : BaseFragment() {

    fun showPopupWindow(view: View, viewModel: MainActivityViewModel){
        val inflater: LayoutInflater = LayoutInflater.from(view.context)
        val popupView = inflater.inflate(R.layout.popup_snooze, null)
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT

        val popupWindow = PopupWindow(popupView, width, height, true)

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        val snoozeBtn = popupView.findViewById(R.id.snooze_accept_btn) as Button
        val snoozeAmountText = popupView.findViewById(R.id.snooze_amount_txt) as TextView
        val snoozeAmountValue = popupView.findViewById(R.id.snooze_amount_value) as SeekBar
        val cancelSnoozeBtn = popupView.findViewById(R.id.cancel_snooze_btn) as Button

        snoozeAmountValue.max = (31 - 2) / 1
        snoozeAmountText.text = "2"

        snoozeAmountValue.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val value = 2 + progress * 1
                if (value == 31){
                    snoozeAmountText.text = view.context.getString(R.string.Sluk_snooze)
                } else {
                    snoozeAmountText.text = "$value"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        snoozeBtn.setOnClickListener {
            if (snoozeAmountText.text == "Sluk") {
                Toast.makeText(view.context, "Notifikationer er blevet slukket", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(view.context, "Notifikationer er blevet snoozet i " + snoozeAmountText.text + " dage", Toast.LENGTH_SHORT).show()
            }
            viewModel.saveUserSnoozeRequest(arrayOf(isLoggedIn(),snoozeAmountText.text.toString()))
            popupWindow.dismiss()
        }

        cancelSnoozeBtn.setOnClickListener {
            popupWindow.dismiss()
        }

        popupView.setOnTouchListener {v: View, m: MotionEvent ->
         //   popupWindow.dismiss()
            false
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