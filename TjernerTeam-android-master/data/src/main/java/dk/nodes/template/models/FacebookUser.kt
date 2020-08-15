package dk.nodes.template.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class FacebookUser(
        @SerializedName("id")
        var facebookId: Long?,
        @SerializedName("name")
        var fullName : String?,
        @SerializedName("email")
        var email : String?,
        @SerializedName("address")
        var address : String?,
        @SerializedName("city")
        var city : String?,
        @SerializedName("phoneNumber")
        var phoneNumber : Long?,
        @SerializedName("postCodes")
        var postCode: PostCode?,
        @SerializedName("fcmToken")
        var fcmToken : String?,
        @SerializedName("notificationSnoozeEndDate")
        var notificationSnoozeEndDate: String?,
        @SerializedName("cprNumber")
        var cprNumber : String?,
        @SerializedName("regNumber")
        var regNumber : Int?,
        @SerializedName("accountNumber")
        var accountNumber : Long?,
        @SerializedName("dateOfBirth")
        var dateOfBirth : String?

) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readParcelable(PostCode::class.java.classLoader),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(facebookId)
        parcel.writeString(fullName)
        parcel.writeString(email)
        parcel.writeString(address)
        parcel.writeString(city)
        parcel.writeValue(phoneNumber)
        parcel.writeParcelable(postCode, flags)
        parcel.writeString(fcmToken)
        parcel.writeString(notificationSnoozeEndDate)
        parcel.writeString(cprNumber)
        parcel.writeValue(regNumber)
        parcel.writeValue(accountNumber)
        parcel.writeString(dateOfBirth)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FacebookUser> {
        override fun createFromParcel(parcel: Parcel): FacebookUser {
            return FacebookUser(parcel)
        }

        override fun newArray(size: Int): Array<FacebookUser?> {
            return arrayOfNulls(size)
        }
    }
}







