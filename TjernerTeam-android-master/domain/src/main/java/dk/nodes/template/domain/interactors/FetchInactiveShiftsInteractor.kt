package dk.nodes.template.domain.interactors

import dk.nodes.template.models.Shift
import dk.nodes.template.repositories.ShiftRepository
import javax.inject.Inject

class FetchInactiveShiftsInteractor  @Inject constructor (private val shiftRepository : ShiftRepository) :  BaseInputAsyncInteractor<Long, ArrayList<Shift>> {
    override suspend fun invoke(input: Long): ArrayList<Shift> {
        return shiftRepository.getInactiveShifts(input)
    }
}