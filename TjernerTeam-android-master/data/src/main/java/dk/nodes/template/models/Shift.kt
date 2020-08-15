package dk.nodes.template.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Shift(
        @SerializedName("id")
        var id: Long?,
        @SerializedName("address")
        var address: String?,
        @SerializedName("salary")
        var salary: Double?,
        @SerializedName("employeeType")
        var employeeType: String?,
        @SerializedName("eventName")
        var eventName: String?,
        @SerializedName("eventDate")
        var eventDate: String?,
        @SerializedName("customerName")
        var customerName: String?,
        @SerializedName("numberOfEmployees")
        var numberOfEmployees: Int?,
        @SerializedName("eventDescription")
        var eventDescription: String?,
        @SerializedName("dressCode")
        var dressCode: String?,
        @SerializedName("paymentDate")
        var paymentDate: String?,
        @SerializedName("startTime")
        var startTime: String?,
        @SerializedName("endTime")
        var endTime: String?,
        @SerializedName("staffFood")
        var staffFood: String?,
        @SerializedName("transportSupplements")
        var transportSupplements: String?,
        @SerializedName("overtime")
        var overtime: String?,
        @SerializedName("city")
        var city: String?,
        @SerializedName("postCodes")
        var postCode: PostCode?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readValue(Double::class.java.classLoader) as? Double,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readParcelable(PostCode::class.java.classLoader)) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(address)
        parcel.writeValue(salary)
        parcel.writeString(employeeType)
        parcel.writeString(eventName)
        parcel.writeString(eventDate)
        parcel.writeString(customerName)
        parcel.writeValue(numberOfEmployees)
        parcel.writeString(eventDescription)
        parcel.writeString(dressCode)
        parcel.writeString(paymentDate)
        parcel.writeString(startTime)
        parcel.writeString(endTime)
        parcel.writeString(staffFood)
        parcel.writeString(transportSupplements)
        parcel.writeString(overtime)
        parcel.writeString(city)
        parcel.writeParcelable(postCode, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Shift> {
        override fun createFromParcel(parcel: Parcel): Shift {
            return Shift(parcel)
        }

        override fun newArray(size: Int): Array<Shift?> {
            return arrayOfNulls(size)
        }
    }


}





