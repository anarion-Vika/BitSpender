package com.example.bitspender.data.remote.btcrate

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isGreaterThan
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import com.example.bitspender.data.remote.BtcRateApi
import com.example.bitspender.data.remote.errorhandling.ApiError
import com.example.bitspender.data.remote.errorhandling.ApiResult
import com.example.bitspender.data.remote.errorhandling.DefaultErrorHandler
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.io.IOException

class BtcRateRemoteDataSourceImplTest {
    private lateinit var btcRateRemoteDataSourceImpl: BtcRateRemoteDataSourceImpl

    private lateinit var btcRateRemoteDataSourceImplWithMock: BtcRateRemoteDataSourceImpl

    private val errorHandler = DefaultErrorHandler()

    private lateinit var apiFake: BtcRateApiFake
    private lateinit var apiMock: BtcRateApi

    private lateinit var mockWebServer: MockWebServer

    @BeforeEach
    fun setUp() {
        apiFake = BtcRateApiFake()
        btcRateRemoteDataSourceImpl = BtcRateRemoteDataSourceImpl(apiFake, errorHandler)


        mockWebServer = MockWebServer()
        apiMock = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create()

        btcRateRemoteDataSourceImplWithMock =
            BtcRateRemoteDataSourceImpl(apiMock, errorHandler)
    }

    @Test
    fun `Testing get BTC rate`() = runBlocking {
        val apiResult = btcRateRemoteDataSourceImpl.getBtcRate()

        assertThat(apiResult is ApiResult.Success).isTrue()

        when (apiResult) {
            is ApiResult.Success -> {
                val btcRate = apiResult.data
                assertThat(btcRate.usd.last).isNotNull()
                assertThat(btcRate.usd.last).isGreaterThan(0.0)
            }

            is ApiResult.Failure -> {
                fail("Expected Success but got Failure: ${apiResult.error}")
            }
        }
    }

    @Test
    fun `If response code 403, then getBtcRate returns is Failure `() = runBlocking {
        mockWebServer.enqueue(
            MockResponse().setResponseCode(403)
        )
        val result = btcRateRemoteDataSourceImplWithMock.getBtcRate()

        assertThat(result is ApiResult.Failure).isTrue()
    }

    @Test
    fun `If api is thrown HttpException with 403 code, then error returns Forbidden`() =
        runBlocking {
            val exception = HttpException(
                Response.error<String>(403, "Forbidden".toResponseBody("text/plain".toMediaType()))
            )

            val error = errorHandler.handle(exception)

            assertThat(error).isInstanceOf(ApiError.Forbidden::class.java)

            val errorForbidden = error as ApiError.Forbidden
            assertThat(errorForbidden.code).isEqualTo(403)
        }

    @Test
    fun `If api is thrown HttpException with 503 code, then error returns ServiceUnavailable`() =
        runBlocking {
            val exception = HttpException(
                Response.error<String>(503, "Service Unavailable".toResponseBody("text/plain".toMediaType()))
            )
            val handler = DefaultErrorHandler()
            val error = handler.handle(exception)

            assertThat(error).isInstanceOf(ApiError.ServiceUnavailable::class.java)
            assertThat((error as ApiError.ServiceUnavailable).code).isEqualTo(503)
        }

    @Test
    fun `If exception is IOException, then returns Network error`() {
        val handler = DefaultErrorHandler()
        val error = handler.handle(IOException("test IOException"))

        assertThat(error).isEqualTo(ApiError.Network)
    }

    @Test
    fun `If api returns 200 with empty body, then result should be Failure with Unknown error`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("")
        )

        val result = btcRateRemoteDataSourceImplWithMock.getBtcRate()

        assertThat(result).isInstanceOf(ApiResult.Failure::class.java)

        val error = (result as ApiResult.Failure).error
        assertThat(error).isInstanceOf(ApiError.Unknown::class.java)
        Unit
    }

    @AfterEach
    fun tearDown() {
        mockWebServer.shutdown()
    }
}