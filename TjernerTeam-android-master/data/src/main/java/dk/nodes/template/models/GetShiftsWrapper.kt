package dk.nodes.template.models

import com.google.gson.annotations.SerializedName

data class GetShiftsWrapper(

        @SerializedName("shiftWrapper")
        var shiftsWrapper: ArrayList<Shift>



){
    override fun toString(): String {
        return "GetShiftsWrapper(shiftsWrapper=$shiftsWrapper)"
    }

}