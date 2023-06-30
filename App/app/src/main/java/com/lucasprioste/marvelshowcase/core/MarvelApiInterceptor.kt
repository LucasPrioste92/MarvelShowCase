package com.lucasprioste.marvelshowcase.core

import com.lucasprioste.marvelshowcase.BuildConfig
import com.lucasprioste.marvelshowcase.data.remote.MarvelApi
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class MarvelApiInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("ts", MarvelApi.TS.toString())
            .addQueryParameter("apikey", BuildConfig.PUBLIC_API_KEY)
            .addQueryParameter("hash", MarvelApi.HASH)
            .build()

        val request: Request = original
            .newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}