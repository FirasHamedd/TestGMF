package com.example.testgmf.shared.testing

import com.example.testgmf.shared.domain.utils.DispatchersProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class DispatchersProviderImplTest
    @OptIn(ExperimentalCoroutinesApi::class)
    constructor(
        override val io: TestDispatcher = StandardTestDispatcher(),
        override val main: TestDispatcher = StandardTestDispatcher(),
        override val mainImmediate: CoroutineDispatcher = StandardTestDispatcher(),
        override val default: TestDispatcher = StandardTestDispatcher(),
        override val unconfined: TestDispatcher = UnconfinedTestDispatcher(),
        override val ioSingleThread: CoroutineDispatcher = UnconfinedTestDispatcher(),
    ) : DispatchersProvider
