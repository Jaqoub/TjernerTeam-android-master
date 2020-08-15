package dk.nodes.template.presentation.ui.shift

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dk.nodes.template.models.FacebookUser
import dk.nodes.template.models.Shift
import dk.nodes.template.presentation.R
import kotlinx.android.synthetic.main.fragment_job.view.*
import kotlinx.android.synthetic.main.shift_recyclerview_row.view.*
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class UserShiftAdapter(val context: Context, val recyclerviewRow: Int) : RecyclerView.Adapter<UserViewHolder>() {
    var onItemClickedListener: ((shift: Shift) -> Unit?)? = null
    val shifts: ArrayList<Shift> = ArrayList()

    override fun getItemCount(): Int {
        return shifts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(context).inflate(recyclerviewRow, parent, false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val eventDateFormatter = DateTimeFormatter.ofPattern("EEE-LLL-YYYY", Locale("da-DK"))
        val date = LocalDate.parse(shifts[position].eventDate).format(eventDateFormatter)
        val dayOfMonth = LocalDate.parse(shifts[position].eventDate).dayOfMonth.toString()


        val dateWeekDay = date.toString().substring(0, 3).toUpperCase()
        val dateMonth = date.toString().substring(5, 8).toUpperCase()

        holder.customerName?.text = shifts[position].customerName
        holder.eventDateWeekDay?.text = dateWeekDay
        holder.eventDateMonthDay?.text = dayOfMonth
        holder.eventDateMonth?.text = dateMonth

        holder.salary?.text = "DKK " + shifts[position].salary?.toBigDecimal()?.setScale(2).toString()
        holder.employee_type?.text = shifts[position].employeeType?.toUpperCase()

        if (holder.employee_type.text == "TJENER") {
            holder.employee_type?.setBackgroundResource(R.drawable.waiter_rounded_corners)
        } else {
            holder.employee_type?.setBackgroundResource(R.drawable.bartender_rounded_corners)
        }

        holder.eventDuration?.text = shifts[position].startTime + " - " + shifts[position].endTime
        holder.address?.text = shifts[position].address
        holder.eventDescription?.text = shifts[position].eventName
        holder.root.setOnClickListener {
            onItemClickedListener?.invoke(shifts.get(position))
        }

    }

    fun addShifts(list: ArrayList<Shift>) {
        shifts.clear()
        Timber.e(list.toString())
        shifts.addAll(list)
    }
}


class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val root = view.shift_cardview
    val customerName = view.customer_name
    val eventDateWeekDay = view.event_date_weekday
    val eventDateMonthDay = view.event_date_monthday
    val eventDateMonth = view.event_date_month
    val salary = view.salary
    val employee_type = view.employee_type_field
    val eventDuration = view.costumer_name_txt
    val address = view.address_txt
    val eventDescription = view.event_description_field
}
