package com.compose.cocktaildakk_compose.network

import okhttp3.Interceptor
import okhttp3.Response

class ServiceInterceptor(
    token: String? = null,
) : Interceptor {

    companion object {
        var userToken: String? = null
    }

    init {
        userToken = token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (request.header("No-Authentication") == null) {
            if (!userToken.isNullOrEmpty()) {
                val finalToken = "$userToken"
                request = request.newBuilder().apply {
                    addHeader("Authorization", finalToken)
                }.build()
            }
        }
        return chain.proceed(request)
    }
}
