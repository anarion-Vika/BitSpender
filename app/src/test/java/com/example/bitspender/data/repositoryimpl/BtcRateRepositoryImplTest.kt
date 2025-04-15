package com.example.bitspender.data.repositoryimpl

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.example.bitspender.data.local.btcrate.BtcRateLocalDataSourceImplFake
import com.example.bitspender.data.mappers.toEntity
import com.example.bitspender.data.models.BtcRateResponse
import com.example.bitspender.data.models.CurrencyRateDto
import com.example.bitspender.data.remote.btcrate.BtcRateRemoteDataSourceImplFake
import com.example.bitspender.data.remote.errorhandling.ApiError
import com.example.bitspender.data.remote.errorhandling.ApiResult
import com.example.bitspender.domain.utils.AppError
import com.example.bitspender.domain.utils.AppResult
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BtcRateRepositoryImplTest {

    private lateinit var repository: BtcRateRepositoryImplFake
    private lateinit var localFake: BtcRateLocalDataSourceImplFake
    private lateinit var remoteFake: BtcRateRemoteDataSourceImplFake

    @BeforeEach
    fun setUp() {
        localFake = BtcRateLocalDataSourceImplFake()
        remoteFake = BtcRateRemoteDataSourceImplFake()

        repository =
            BtcRateRepositoryImplFake(localFake, remoteFake)
    }

    @Test
    fun `updateBtcRate returns Success when remote source returns valid rate`() = runTest {

        val fakeRemote = BtcRateRemoteDataSourceImplFake().apply {
            apiResult = ApiResult.Success(BtcRateResponse(CurrencyRateDto(10.0)))
        }
        val fakeLocal = BtcRateLocalDataSourceImplFake()
        val repo = BtcRateRepositoryImplFake(fakeLocal, fakeRemote)

        val result = repo.updateBtcRate()

        assertThat(result).isInstanceOf(AppResult.Success::class.java)
        assertThat(fakeLocal.btcRate?.rate).isEqualTo(10.0)
    }


    @Test
    fun `updateBtcRate returns Error when remote source fails`() = runTest {
        val fakeRemote = BtcRateRemoteDataSourceImplFake().apply {
            apiResult = ApiResult.Failure(ApiError.Network)
        }
        val fakeLocal = BtcRateLocalDataSourceImplFake()
        val repo = BtcRateRepositoryImplFake(fakeLocal, fakeRemote)

        val result = repo.updateBtcRate()

        assertThat(result).isInstanceOf(AppResult.Error::class.java)
        val error = (result as AppResult.Error).error
        assertThat(error).isEqualTo(AppError.Network)
    }

    @Test
    fun `getCurrentRate returns correct BtcRateModel`() = runTest {
        val rate = 15.5
        val fakeLocal = BtcRateLocalDataSourceImplFake().apply {
            btcRate = BtcRateResponse(CurrencyRateDto(rate)).toEntity()
        }
        val fakeRemote = BtcRateRemoteDataSourceImplFake()
        val repo = BtcRateRepositoryImplFake(fakeLocal, fakeRemote)

        repo.getCurrentRate().test {
            val emission = awaitItem()
            assertThat(emission?.rate).isEqualTo(rate)
            awaitComplete()
        }
    }


}