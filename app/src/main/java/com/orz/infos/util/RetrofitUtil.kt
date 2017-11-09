package com.orz.infos.util

import com.orz.infos.App
import com.orz.infos.Constant
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Logger
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by Administrator on 2017/10/20.
 */
class RetrofitUtil private constructor(){

    private var mRetrofit: Retrofit? = null

    companion object {
        fun getInstance(): RetrofitUtil{
            return Inner.single
        }
    }

    private object Inner{
        val single = RetrofitUtil()
    }

    fun <T> getService(url: String, service: Class<T>): T{
        return create(url).create(service)
    }

    private fun create(url: String): Retrofit{
        if (null == mRetrofit){
            var okHttpBuilder: OkHttpClient.Builder  = getOkHttpClientBuilder()
            okHttpBuilder.addInterceptor(getLoggingInterceptor())
            okHttpBuilder.addInterceptor(getCacheInterceptor())
            mRetrofit = Retrofit.Builder().baseUrl(url)
                    .client(okHttpBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()
        }
        return mRetrofit!!
    }

    private fun  getLoggingInterceptor(): Interceptor {
        var loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor(Logger {
            message -> LogUtil.d(message)
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    private fun getCacheInterceptor(): Interceptor{
        return MyCacheInterceptor()
    }

    private fun  getOkHttpClientBuilder(): OkHttpClient.Builder {
        var builder: OkHttpClient.Builder = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
        var file: File = App.context.getExternalFilesDir(Constant.NET_CACHE_PATH)
        if (file != null){
            if (!file.exists()){
                file.mkdir()
            }
            var cache: Cache = Cache(file, Constant.DEFAULT_NET_CACHE_SIZE)
            builder.cache(cache)
        }
        return builder
    }

    private class MyCacheInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            var request = chain!!.request()
            if (!NetWorkUtil.isNetWorkAvailable(App.context)){
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            var response: Response = chain.proceed(request)
            var newResponse: Response
            if (NetWorkUtil.isNetWorkAvailable(App.context)){
                var maxAge = 60 * 10
                newResponse = response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age = " + maxAge)
                        .build()
            }else{
                var maxStale: Int = 60 * 60 * 24 * 7
                newResponse = response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale = " + maxStale)
                        .build()
            }
            return newResponse
        }
    }
}
