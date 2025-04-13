package com.example.bitspender.data.remote.errorhandling

import okhttp3.Request
import okio.Timeout
import retrofit2.*

class ApiResultCall<T>(
    private val call: Call<T>,
    private val errorHandler: ErrorHandler
) : Call<ApiResult<T>> {

    override fun enqueue(callback: Callback<ApiResult<T>>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val result: ApiResult<T> = if (response.isSuccessful) {
                    response.body()?.let {
                        ApiResult.Success(it)
                    }
                        ?: ApiResult.Failure(ApiError.Unknown(NullPointerException("Response body is null")))
                } else {
                    ApiResult.Failure(ApiError.Http(response.code(), response.message()))
                }

                callback.onResponse(this@ApiResultCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val error = errorHandler.handle(t)
                val failure = ApiResult.Failure(error)
                callback.onResponse(this@ApiResultCall, Response.success(failure))
            }
        })

    }

    override fun clone(): Call<ApiResult<T>> = ApiResultCall(call.clone(), errorHandler)
    override fun execute(): Response<ApiResult<T>> = throw NotImplementedError()
    override fun isExecuted(): Boolean = call.isExecuted
    override fun cancel() = call.cancel()
    override fun isCanceled(): Boolean = call.isCanceled
    override fun request(): Request = call.request()
    override fun timeout(): Timeout = call.timeout()
}
