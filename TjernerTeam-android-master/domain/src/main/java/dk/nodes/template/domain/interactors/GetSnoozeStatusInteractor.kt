package dk.nodes.template.domain.interactors

import android.util.Log
import dk.nodes.template.models.FacebookUser
import dk.nodes.template.models.SnoozeStatusAndDaysLeft
import dk.nodes.template.repositories.FacebookRespository
import javax.inject.Inject

class GetSnoozeStatusInteractor @Inject constructor(private val facebookRespository: FacebookRespository)
    : BaseInputAsyncInteractor<String, SnoozeStatusAndDaysLeft> {
    override suspend fun invoke(input: String): SnoozeStatusAndDaysLeft {
        return facebookRespository.checkUserSnoozeStatus(input)
    }
}