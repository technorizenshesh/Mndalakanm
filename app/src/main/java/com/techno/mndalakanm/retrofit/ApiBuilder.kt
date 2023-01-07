package com.cityoneprovider.retrofit

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


object ApiBuilder {
    private val retrofit: Retrofit? = null
    private const val TIMEOUT_IN_SECONDS = 60

    fun <T> create(serviceInterface: Class<T>?): T? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor)
            .connectTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS).build()

        // val gson: Gson = GsonBuilder().create()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ApiConstant.BASE_URL)
            .client(client)
           // .client(getUnsafeOkHttpClient(context).build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return serviceInterface?.let { retrofit.create(it) }
    }

    fun getUnsafeOkHttpClient(context: Context?): OkHttpClient.Builder {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate>,
                        authType: String
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            val builder: OkHttpClient.Builder = OkHttpClient.Builder()
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.connectTimeout(60, TimeUnit.SECONDS) // connect timeout
                .writeTimeout(60, TimeUnit.SECONDS) // write timeout
                .readTimeout(60, TimeUnit.SECONDS) // read timeout
                .addInterceptor(interceptor)
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(HostnameVerifier { hostname, session -> true })
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}