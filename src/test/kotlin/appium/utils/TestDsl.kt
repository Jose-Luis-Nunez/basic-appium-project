package appium.utils

import org.fluentlenium.adapter.junit.jupiter.FluentTest

import org.fluentlenium.core.FluentPage


abstract class TestDsl: FluentTest() {

    inline operator fun <reified T : FluentPage> T.invoke(func: T.() -> Unit) = with(T::class) {
        apply { func() }
    }

    fun assertThat(func: FluentPage.() -> Unit = {}) {
        FluentPage().func()
    }
}