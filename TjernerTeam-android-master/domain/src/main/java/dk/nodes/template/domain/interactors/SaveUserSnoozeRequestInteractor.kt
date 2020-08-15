package dk.nodes.template.domain.interactors

import dk.nodes.template.repositories.FacebookRespository
import javax.inject.Inject

class SaveUserSnoozeRequestInteractor @Inject constructor(private val facebookRespository: FacebookRespository)
    : BaseInputAsyncInteractor<Array<String>, Unit> {
    override suspend fun invoke(input: Array<String>) {
        return facebookRespository.saveUserSnoozeRequest(input)
    }
}