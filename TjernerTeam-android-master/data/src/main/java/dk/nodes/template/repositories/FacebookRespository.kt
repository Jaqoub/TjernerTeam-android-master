package dk.nodes.template.repositories

import dk.nodes.template.models.FacebookUser
import dk.nodes.template.models.SnoozeStatusAndDaysLeft
import dk.nodes.template.network.FaceBookService
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class FacebookRespository @Inject constructor(
        private val api: FaceBookService) {

    suspend fun saveUser(User: FacebookUser) {
        val response = api.saveFacebookUser(User.facebookId, User.fullName, User.email, User.fcmToken).execute()

        if (!response.isSuccessful) {
        }
    }

    suspend fun saveUserRequestedJob(userAndShiftId: Array<Long?>) {
        val response = api.saveUserRequestedJob(userAndShiftId[0], userAndShiftId[1]).execute()

        if (!response.isSuccessful) {
        }
    }

    suspend fun saveUserCheckout(userCheckoutShift: Array<String?>) {
        val response = api.saveUserCheckout(userCheckoutShift[0],userCheckoutShift[1],userCheckoutShift[2],userCheckoutShift[3]).execute()

        if (!response.isSuccessful) {
        }
    }


    suspend fun cancelAssignedJob(userAndShiftId: Array<Long?>) {
        val response = api.cancelAssignedJob(userAndShiftId[0], userAndShiftId[1]).execute()

        if (!response.isSuccessful) {
        }
    }


    suspend fun saveUserSnoozeRequest(userIdAndSnoozeValue: Array<String>) {
        val response = api.saveUserSnoozeRequest(userIdAndSnoozeValue[0], userIdAndSnoozeValue[1]).execute()
        if (!response.isSuccessful) {
        }
    }

    fun getFacebookUser(facebookUser: String): FacebookUser {
            var user: FacebookUser? = null

            val response = api.getFacebookUser(facebookUser).execute()

            if (response.isSuccessful) {
                user = response.body()

                if (user != null) {

                    return user
                }
            }

            return user!!

        }

        suspend fun deleteUser(facebookId: Long) {

            val response = api.deleteUser(facebookId).execute()

            if (!response.isSuccessful) {


            }


        }


        suspend fun updateUser(user: FacebookUser) {

            val response = api.updateUSer(user.facebookId, user.fullName, user.email, user.address, user.city, user.phoneNumber, user.postCode?.postCode, user.cprNumber, user.regNumber, user.accountNumber, user.dateOfBirth).execute()

            if (response != null) {
                if (!response.isSuccessful) {
                }
            }
        }

       suspend fun checkUserSnoozeStatus(facebookUser: String): SnoozeStatusAndDaysLeft {
            var user = getFacebookUser(facebookUser)
            var snoozeDaysLeft = ""
            val snoozeEndDate = user.notificationSnoozeEndDate
            val currentDate = LocalDate.now()
            val userIsSnoozed: Boolean

            if (snoozeEndDate == null) {

                snoozeDaysLeft = "Sluk"
                userIsSnoozed = true
            } else if (LocalDate.parse(snoozeEndDate).isAfter(currentDate)) {

                snoozeDaysLeft = currentDate.until(LocalDate.parse(snoozeEndDate), ChronoUnit.DAYS).toString()
                userIsSnoozed = true
            } else {
                userIsSnoozed = false

            }
            return SnoozeStatusAndDaysLeft(snoozeEndDate, userIsSnoozed)
        }

}