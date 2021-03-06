package dk.nodes.template.domain.interactors

import dk.nodes.template.repositories.FacebookRespository
import javax.inject.Inject

class CancelAssignedJobInteractor @Inject constructor(private val facebookRespository: FacebookRespository)
    : BaseInputAsyncInteractor<Array<Long?>, Unit> {
    override suspend fun invoke(input: Array<Long?>) {
        return facebookRespository.cancelAssignedJob(input)
    }
}