package com.example.bitspender.domain.usecases.btcrate

import com.example.bitspender.domain.repositories.BtcRateRepository
import com.example.bitspender.domain.utils.AppResult
import javax.inject.Inject

class UpdateBtcRateUseCase @Inject constructor(
    private val repository: BtcRateRepository
) {

    suspend operator fun invoke(): AppResult<Unit> {
        return repository.updateBtcRate()
    }
}