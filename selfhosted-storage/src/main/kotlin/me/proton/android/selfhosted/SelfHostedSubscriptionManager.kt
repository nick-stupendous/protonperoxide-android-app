package me.proton.android.selfhosted

interface SelfHostedSubscriptionManager {
    suspend fun getCurrentPlan(): FreemiumPlan
    suspend fun getStorageStats(): StorageStats
    fun isFeatureEnabled(feature: FeatureFlag): Boolean
}

enum class FeatureFlag {
    VPN, PASSWORD_MANAGER, CLOUD_STORAGE, CUSTOM_DOMAINS, HIDE_MY_EMAIL
}

data class FreemiumPlan(
    val maxUsers: Int = 2,
    val extraEmailAddresses: Int = 30,
    val customDomains: Int = 3,
    val unlimitedFolders: Boolean = true,
    val unlimitedLabels: Boolean = true,
    val unlimitedHideMyEmailAliases: Boolean = true,
    val dedicatedSupport: Boolean = true
)

data class StorageStats(
    val totalBytes: Long,
    val freeBytes: Long,
    val usedBytes: Long
)
