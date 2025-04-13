package com.example.bitspender.data.remote.errorhandling

import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiResponseCallAdapterFactory(
    private val errorHandler: ErrorHandler
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        val rawType = getRawType(returnType)
        if (rawType != ApiResult::class.java) return null

        val successType = getParameterUpperBound(0, returnType as ParameterizedType)
        return ApiResponseCallAdapter<Any>(successType, errorHandler)
    }
}
