package dk.nodes.template.domain.interactors

import dk.nodes.template.repositories.FacebookRespository
import javax.inject.Inject

class SaveUserCheckoutInteractor @Inject constructor(private val facebookRespository: FacebookRespository)
    : BaseInputAsyncInteractor<Array<String?>, Unit> {
    override suspend fun invoke(input: Array<String?>) {
        return facebookRespository.saveUserCheckout(input)
    }
}