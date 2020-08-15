package dk.nodes.template.presentation.ui.shift

import android.app.TimePickerDialog
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.facebook.AccessToken
import dk.nodes.template.models.Shift
import dk.nodes.template.presentation.R
import dk.nodes.template.presentation.ui.base.BaseFragment
import dk.nodes.template.presentation.ui.main.MainActivityViewModel
import java.text.SimpleDateFormat
import java.util.*

class CheckoutFragment : BaseFragment() {

    fun showPopupWindow(view: View, viewModel: MainActivityViewModel, shift : Shift){
        val inflater: LayoutInflater = LayoutInflater.from(view.context)
        val popupView = inflater.inflate(R.layout.popup_checkout, null)
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT

        val popupWindow = PopupWindow(popupView, width, height, true)

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)

        val checkoutBtn = popupView.findViewById(R.id.checkout_confirm_btn) as Button
        val checkinText = popupView.findViewById(R.id.checkin_txt) as TextView
        val checkoutText = popupView.findViewById(R.id.checkout_txt) as TextView
        val cancelCheckoutBtn = popupView.findViewById(R.id.cancel_checkout_btn) as Button

        checkinText.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view: TimePicker?, hour: Int, minute: Int ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                checkinText.text = SimpleDateFormat("HH:mm").format(cal.time)

            }

            TimePickerDialog(view.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

        }

        checkoutText.setOnClickListener{
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view: TimePicker?, hour: Int, minute: Int ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                checkoutText.text = SimpleDateFormat("HH:mm").format(cal.time)

            }

            TimePickerDialog(view.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()

        }


        checkoutBtn.setOnClickListener {

            Toast.makeText(view.context, "Du er nu blevet tjekket ud!", Toast.LENGTH_SHORT).show()

            viewModel.saveUserCheckout(isLoggedIn(),checkinText.text.toString(),checkoutText.text.toString(),shift?.id.toString())
            popupWindow.dismiss()
        }

        cancelCheckoutBtn.setOnClickListener {
            popupWindow.dismiss()
        }

        popupView.setOnTouchListener { v: View, m: MotionEvent ->
           // popupWindow.dismiss()
            true
        }
    }

    fun isLoggedIn(): String? {
        val accessToken = AccessToken.getCurrentAccessToken()
        if (accessToken != null) {
            return accessToken.userId
        }
        return "-1"
    }
}