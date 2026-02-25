package com.example.testgmf.shared.testing

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(MainDispatcherExtension::class)
interface MainDispatcherTest {
    var dispatchersProvider: DispatchersProviderImplTest
}
