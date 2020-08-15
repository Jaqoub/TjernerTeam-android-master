package dk.nodes.template.network

import dk.nodes.template.models.GetShiftsWrapper
import dk.nodes.template.models.Shift
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

interface ShiftService {
         @GET("/getAllShifts")
         fun getActiveShifts(@Query("facebook_id") userId: Long?) : Call<GetShiftsWrapper>

        @GET("/getUserActiveShifts")
        fun getAllUserActiveShifts(@Query("facebook_id") userId: Long?) : Call<GetShiftsWrapper>

        @GET("/getUserInactiveShifts")
        fun getAllUserInactiveShifts(@Query("facebook_id") userId: Long?) : Call<GetShiftsWrapper>
}