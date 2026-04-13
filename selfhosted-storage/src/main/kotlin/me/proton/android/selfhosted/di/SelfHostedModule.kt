package me.proton.android.selfhosted.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.proton.android.selfhosted.EntitlementDatabase
import me.proton.android.selfhosted.RustFsStorageManager
import me.proton.android.selfhosted.SelfHostedSubscriptionManager
import me.proton.android.selfhosted.FreemiumPlan
import me.proton.android.selfhosted.StorageStats
import me.proton.android.selfhosted.FeatureFlag
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SelfHostedModule {
    
    @Provides
    @Singleton
    fun provideRustFsStorageManager(): RustFsStorageManager {
        val rustFsUrl = "http://192.168.1.100:8080/"
        return RustFsStorageManager(rustFsUrl)
    }
    
    @Provides
    @Singleton
    fun provideEntitlementDatabase(@ApplicationContext context: Context): EntitlementDatabase {
        return EntitlementDatabase(context)
    }
    
    @Provides
    @Singleton
    fun provideSelfHostedSubscriptionManager(
        storageManager: RustFsStorageManager,
        database: EntitlementDatabase
    ): SelfHostedSubscriptionManager {
        return object : SelfHostedSubscriptionManager {
            override suspend fun getCurrentPlan(): FreemiumPlan = FreemiumPlan()
            
            override suspend fun getStorageStats(): StorageStats {
                return StorageStats(
                    totalBytes = storageManager.getTotalBytes(),
                    freeBytes = storageManager.getFreeBytes(),
                    usedBytes = storageManager.getUsedBytes()
                )
            }
            
            override fun isFeatureEnabled(feature: FeatureFlag): Boolean {
                return database.isFeatureEnabled(feature.name.lowercase(), "default-key-change-this")
            }
        }
    }
}
