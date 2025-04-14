package com.example.bitspender.domain.usecases.btcrate

import com.example.bitspender.domain.repositories.BtcRateRepository
import com.example.bitspender.domain.models.BtcRateModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBtcRateUseCase @Inject constructor(
    private val repository: BtcRateRepository
) {

    operator fun invoke(): Flow<BtcRateModel?> {
        return repository.getCurrentRate()
    }
}