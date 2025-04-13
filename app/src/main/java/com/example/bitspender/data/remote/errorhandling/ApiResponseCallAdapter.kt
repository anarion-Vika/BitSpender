package com.example.bitspender.data.remote.errorhandling

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ApiResponseCallAdapter<T>(
    private val responseType: Type,
    private val errorHandler: ErrorHandler
) : CallAdapter<T, Call<ApiResult<T>>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Call<ApiResult<T>> {
        return ApiResultCall(call, errorHandler)
    }
}