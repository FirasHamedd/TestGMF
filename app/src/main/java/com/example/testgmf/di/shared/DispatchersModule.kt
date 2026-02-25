package com.example.testgmf.di.shared

import com.example.testgmf.shared.domain.utils.DispatchersProvider
import com.example.testgmf.shared.domain.utils.DispatchersProviderImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {
    @Provides
    fun provideDispatchers(): DispatchersProvider = DispatchersProviderImpl()
}
