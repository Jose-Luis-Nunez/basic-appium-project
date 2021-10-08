package appium.utils

import org.fluentlenium.adapter.junit.jupiter.FluentTest
import org.fluentlenium.assertj.FluentLeniumAssertions
import org.fluentlenium.assertj.custom.FluentListAssert
import org.fluentlenium.assertj.custom.FluentWebElementAssert
import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement

abstract class TestDsl: FluentTest() {

    inline operator fun <reified T : FluentPage> T.invoke(func: T.() -> Unit) = with(T::class) {
        apply { func() }
    }

    fun FluentWebElement.assertThat(init: FluentWebElementAssert.() -> Unit) {
        FluentLeniumAssertions.assertThat(this).init()
    }

    fun FluentList<FluentWebElement>.assert(init: FluentListAssert.() -> Unit) {
        FluentLeniumAssertions.assertThat(this).init()
    }

    operator fun FluentWebElement.invoke(init: FluentWebElementAssert.() -> Unit) {
        FluentLeniumAssertions.assertThat(this).init()
    }

    fun assertThat(func: FluentPage.() -> Unit = {}) {
        FluentPage().func()
    }
}