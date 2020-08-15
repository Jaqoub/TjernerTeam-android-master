package dk.nodes.template.domain.interactors

import dk.nodes.template.models.FacebookUser
import dk.nodes.template.repositories.FacebookRespository
import javax.inject.Inject

class FetchFacebookUserInteractor @Inject constructor(
        private val facebookRespository: FacebookRespository): BaseInputAsyncInteractor<String, FacebookUser>{
        override suspend fun invoke(input: String): FacebookUser{
            return facebookRespository.getFacebookUser(input)

        }



}