package me.proton.android.selfhosted

import retrofit2.http.GET

interface RustFsApi {
    @GET("/api/v1/capacity")
    suspend fun getCapacity(): CapacityResponse
}

data class CapacityResponse(
    val totalBytes: Long,
    val freeBytes: Long,
    val usedBytes: Long
)
