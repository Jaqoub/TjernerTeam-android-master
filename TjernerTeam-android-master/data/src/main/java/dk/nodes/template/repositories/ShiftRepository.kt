package dk.nodes.template.repositories

import com.google.gson.Gson
import dk.nodes.template.models.Shift
import dk.nodes.template.network.ShiftService
import javax.inject.Inject

class ShiftRepository @Inject constructor(
        private val api: ShiftService,
        private val gson: Gson) {

   suspend fun getShifts(userId: Long): ArrayList<Shift> {
        val shiftsArrayList =ArrayList<Shift>()
        val response = api.getActiveShifts(userId).execute()

        if (response.isSuccessful) {
           val shiftResponse = response.body()
            if (shiftResponse != null){
                shiftsArrayList.addAll(shiftResponse.shiftsWrapper)
                return shiftsArrayList
            }
        }
        return shiftsArrayList
    }

    suspend fun getActiveShifts(userId: Long): ArrayList<Shift> {
        val shiftsArrayList =ArrayList<Shift>()
        val response = api.getAllUserActiveShifts(userId).execute()

        if (response.isSuccessful) {
            val shiftResponse = response.body()
            if (shiftResponse != null){
                shiftsArrayList.addAll(shiftResponse.shiftsWrapper)
                return shiftsArrayList
            }
        }
        return shiftsArrayList
    }

    suspend fun getInactiveShifts(userId: Long): ArrayList<Shift> {
        val shiftsArrayList =ArrayList<Shift>()
        val response = api.getAllUserInactiveShifts(userId).execute()
        if (response.isSuccessful) {
            val shiftResponse = response.body()
            if (shiftResponse != null){
                shiftsArrayList.addAll(shiftResponse.shiftsWrapper)
                return shiftsArrayList
            }
        }
        return shiftsArrayList
    }
}

