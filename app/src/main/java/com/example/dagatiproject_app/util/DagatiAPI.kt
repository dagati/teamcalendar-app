package com.example.dagatiproject_app.util

import android.annotation.SuppressLint
import com.example.dagatiproject_app.data.LoginRequest
import com.example.dagatiproject_app.data.LoginResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

interface DagatiAPI {

    @POST(APIURL.login)
    suspend fun requestLogin(
        @Body body: LoginRequest
    ): Response<LoginResponse>


    companion object {
        fun create(): DagatiAPI {

            val client = OkHttpClient.Builder()
                .readTimeout(30000, TimeUnit.MILLISECONDS)

            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            client.addInterceptor(httpLoggingInterceptor)

            try {
                val trustAllCerts = arrayOf<TrustManager>(
                    @SuppressLint("CustomX509TrustManager")
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
                client.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                client.hostnameVerifier { _: String?, _: SSLSession? -> true }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return Retrofit.Builder()
                .baseUrl(APIURL.baseURL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DagatiAPI::class.java)
        }
    }

}