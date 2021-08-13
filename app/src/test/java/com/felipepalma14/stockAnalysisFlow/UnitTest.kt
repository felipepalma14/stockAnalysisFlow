package com.felipepalma14.stockAnalysisFlow

import org.junit.Assert.*
import org.junit.Rule


abstract class UnitTest {
    @Suppress("LeakingThis")
    @Rule
    @JvmField
    val injectMocks = InjectMocksRule.create(this@UnitTest)

}