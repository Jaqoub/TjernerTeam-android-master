package dk.nodes.template.domain.interactors

import dk.nodes.template.models.FacebookUser
import dk.nodes.template.repositories.FacebookRespository
import javax.inject.Inject

class DeleteUserInteractor  @Inject constructor(private val facebookRespository : FacebookRespository)
    :  BaseInputAsyncInteractor<Long,Unit> {
    override suspend fun invoke(input: Long) {
        return facebookRespository.deleteUser(input)
    }

}