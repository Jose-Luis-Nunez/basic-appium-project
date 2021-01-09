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

    private lateinit var appiumDriver: AppiumDriver<*>

    private val device: Device = Android()

    override fun newWebDriver(): WebDriver {
        log.info("Running test on Appium server {} using {}", PropertiesReader().getAppiumServerUrl(), device)
        return runTestOnAppiumServer()
    }

    private fun runTestOnAppiumServer(): WebDriver {
        try {
            appiumDriver = AppiumDriver<WebElement>(URL(PropertiesReader().getAppiumServerUrl()), capabilities)
            appiumDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS)
            return appiumDriver
        } catch (e: MalformedURLException) {
            throw ConfigException("Invalid hub location: " + PropertiesReader().getAppiumServerUrl(), e)
        }
    }

    override fun getCapabilities() = device.getCapabilities()!!

    companion object {
        private val log = LoggerFactory.getLogger(AppiumTestSetup::class.java)
    }
}