package appium.utils

import appium.config.ConfigException
import appium.device.Android
import appium.device.Device
import io.appium.java_client.AppiumDriver
import org.fluentlenium.adapter.junit.jupiter.FluentTest
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.slf4j.LoggerFactory
import java.net.MalformedURLException
import java.net.URL
import java.util.concurrent.TimeUnit

open class AppiumTestSetup : FluentTest() {

    private val device: Device = Android()
    private val appiumServerUrl: String? = PropertiesReader().getProp("appium.server.url")

    override fun newWebDriver(): WebDriver {
        log.info("Running test on Appium server {} using {}", appiumServerUrl, device)
        return runTestOnAppiumServer()
    }

    private fun runTestOnAppiumServer(): WebDriver {
        try {
            val appiumDriver = AppiumDriver<WebElement>(URL(appiumServerUrl), capabilities)
            appiumDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
            return appiumDriver
        } catch (e: MalformedURLException) {
            throw ConfigException("Invalid hub location: $appiumServerUrl", e)
        }
    }

    override fun getCapabilities() = device.getCapabilities()!!

    companion object {
        private val log = LoggerFactory.getLogger(AppiumTestSetup::class.java)
    }
}