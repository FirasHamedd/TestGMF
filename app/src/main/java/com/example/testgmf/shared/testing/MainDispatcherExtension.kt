package com.example.testgmf.shared.testing

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.TestInstancePostProcessor

@ExperimentalCoroutinesApi
class MainDispatcherExtension(
    private val dispatcher: TestDispatcher = StandardTestDispatcher(),
) : BeforeEachCallback,
    AfterEachCallback,
    TestInstancePostProcessor {
    /**
     * Set TestCoroutine dispatcher as main
     */
    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(dispatcher)
    }

    override fun postProcessTestInstance(
        testInstance: Any?,
        context: ExtensionContext?,
    ) {
        (testInstance as? MainDispatcherTest)?.let {
            it.dispatchersProvider = DispatchersProviderImplTest(
                io = dispatcher,
                main = dispatcher,
                default = dispatcher,
                unconfined = dispatcher,
            )
        }
    }

    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }
}
