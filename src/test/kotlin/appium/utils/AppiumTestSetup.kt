package appium.utils

import appium.config.ConfigException
import appium.platform.Android
import appium.platform.Platform
import io.appium.java_client.AppiumDriver
import org.openqa.selenium.WebDriver
import org.slf4j.LoggerFactory
import java.net.MalformedURLException
import java.net.URL
import java.time.Duration

private const val APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub"

open class AppiumTestSetup : TestDsl() {
    private val timeout = Integer.getInteger("page_load_timeout", 30).toLong()
    private val platform: Platform = Android()


    override fun getCapabilities() = platform.getCapabilities()

    override fun newWebDriver(): WebDriver {
        log.info("Running test on Appium server {} using {}", APPIUM_SERVER_URL, platform)
        return runTestOnAppiumServer()
    }

    private fun runTestOnAppiumServer(): WebDriver {
        try {
            val appiumDriver = AppiumDriver(URL(APPIUM_SERVER_URL), capabilities)
            appiumDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout))
            return appiumDriver
        } catch (e: MalformedURLException) {
            throw ConfigException("Invalid hub location: $APPIUM_SERVER_URL", e)
        }
    }

    companion object {
        private val log = LoggerFactory.getLogger(AppiumTestSetup::class.java)
    }
}
