package me.proton.android.selfhosted

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

class RustFsStorageManager(private val baseUrl: String) {
    private val api: RustFsApi by lazy {
        val client = OkHttpClient.Builder()
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build()
        
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RustFsApi::class.java)
    }

    suspend fun getTotalBytes(): Long = api.getCapacity().totalBytes
    suspend fun getFreeBytes(): Long = api.getCapacity().freeBytes
    suspend fun getUsedBytes(): Long = api.getCapacity().usedBytes
}
