package dk.zibralabs.m2call.migo.datasource

import okhttp3.Interceptor

object InterceptorManager {

    fun commonInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
//            val requestBuilder = if (Const.token.isNotEmpty())
//                request.newBuilder().addHeader("Accept", "application/json")
//            else request.newBuilder()
//                .addHeader("Accept", "*/*")
//                .addHeader("Content-Type", "application/x-www-form-urlencoded")
            val requestBuilder = request.newBuilder().addHeader("Accept", "application/json")
            chain.proceed(requestBuilder.build())
        }
    }

}