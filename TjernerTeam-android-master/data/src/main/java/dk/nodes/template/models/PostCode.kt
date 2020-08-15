package dk.nodes.template.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class PostCode (

       @SerializedName("postCode")
       var postCode : Int?
) : Parcelable{
    constructor(parcel: Parcel) : this(parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(postCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostCode> {
        override fun createFromParcel(parcel: Parcel): PostCode {
            return PostCode(parcel)
        }

        override fun newArray(size: Int): Array<PostCode?> {
            return arrayOfNulls(size)
        }
    }


}