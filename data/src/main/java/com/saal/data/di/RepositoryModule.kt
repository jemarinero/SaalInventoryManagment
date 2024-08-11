package com.saal.data.di

import com.saal.data.repository.ItemRepositoryImpl
import com.saal.data.repository.ItemTypeRepositoryImpl
import com.saal.data.repository.RelationRepositoryImpl
import com.saal.domain.repository.ItemRepository
import com.saal.domain.repository.ItemTypeRepository
import com.saal.domain.repository.RelationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [])
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Module
    @InstallIn(SingletonComponent::class)
    internal interface Bindings {

        @Binds
        fun bindItemRepository(impl: ItemRepositoryImpl): ItemRepository

        @Binds
        fun bindItemTypeRepository(impl: ItemTypeRepositoryImpl): ItemTypeRepository

        @Binds
        fun bindRelationRepository(impl: RelationRepositoryImpl): RelationRepository

    }
}