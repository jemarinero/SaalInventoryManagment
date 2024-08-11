package com.saal.domain.common.di

import com.saal.domain.common.distpatcher.DefaultDispatcherProvider
import com.saal.domain.common.distpatcher.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Binds
    fun bindDispatcherProvider(imp: DefaultDispatcherProvider): DispatcherProvider
}