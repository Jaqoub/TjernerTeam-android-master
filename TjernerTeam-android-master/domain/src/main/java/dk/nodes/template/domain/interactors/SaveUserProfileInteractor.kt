package dk.nodes.template.domain.interactors

import dk.nodes.template.models.FacebookUser
import dk.nodes.template.repositories.FacebookRespository
import javax.inject.Inject

class SaveUserProfileInteractor @Inject constructor(private val FacebookRespository : FacebookRespository)
    :  BaseInputAsyncInteractor<FacebookUser,Unit> {
    override suspend fun invoke(input: FacebookUser) {
        return FacebookRespository.updateUser(input)
    }
}


