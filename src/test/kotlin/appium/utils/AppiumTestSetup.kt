package appium.utils

import appium.config.ConfigException
import appium.device.Android
import appium.device.Platform
import io.appium.java_client.AppiumDriver
import org.fluentlenium.adapter.junit.jupiter.FluentTest
import org.fluentlenium.assertj.FluentLeniumAssertions
import org.fluentlenium.assertj.custom.FluentListAssert
import org.fluentlenium.assertj.custom.FluentWebElementAssert
import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.domain.FluentList
import org.fluentlenium.core.domain.FluentWebElement
import org.junit.runner.RunWith
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.slf4j.LoggerFactory
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.TimeUnit

private const val APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub"

@RunWith(Parallelized::class)
open class AppiumTestSetup : FluentTest() {

    private val platform: Platform = Android()

    override fun newWebDriver(): WebDriver {
        log.info("Running test on Appium server {} using {}", APPIUM_SERVER_URL, platform)
        return runTestOnAppiumServer()
    }

    private fun runTestOnAppiumServer(): WebDriver {
        try {
            val appiumDriver = AppiumDriver<WebElement>(URL(APPIUM_SERVER_URL), capabilities)
            appiumDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS)
            return appiumDriver
        } catch (e: MalformedURLException) {
            throw ConfigException("Invalid hub location: $APPIUM_SERVER_URL", e)
        }
    }

    override fun getCapabilities() = platform.getCapabilities()!!

    companion object {
        private val log = LoggerFactory.getLogger(AppiumTestSetup::class.java)
    }

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