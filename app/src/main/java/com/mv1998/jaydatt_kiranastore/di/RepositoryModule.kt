package com.mv1998.jaydatt_kiranastore.di

import com.mv1998.jaydatt_kiranastore.data.repository.MergeRepositoryImpl
import com.mv1998.jaydatt_kiranastore.data.repository.ProductRepositoryImpl
import com.mv1998.jaydatt_kiranastore.data.repository.ReportRepositoryImpl
import com.mv1998.jaydatt_kiranastore.domain.repository.MergeRepository
import com.mv1998.jaydatt_kiranastore.domain.repository.ProductRepository
import com.mv1998.jaydatt_kiranastore.domain.repository.ReportRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ) : ProductRepository

    @Singleton
    @Binds
    abstract fun bindsReportRepository(
        reportRepositoryImpl: ReportRepositoryImpl
    ) : ReportRepository

    @Singleton
    @Binds
    abstract fun bindsMergeRepository(
        mergeRepositoryImpl: MergeRepositoryImpl
    ) : MergeRepository
}