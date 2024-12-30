package com.mv1998.jaydatt_kiranastore.di

import android.content.Context
import com.mv1998.jaydatt_kiranastore.data.local.InventoryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InventoryModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = InventoryDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideProductDao(
        inventoryDatabase: InventoryDatabase
    ) = inventoryDatabase.getProductDao()

    @Singleton
    @Provides
    fun provideReportDao(
        inventoryDatabase: InventoryDatabase
    ) = inventoryDatabase.getReportDao()

    @Singleton
    @Provides
    fun provideCoroutineDispatchers() = Dispatchers.IO



}