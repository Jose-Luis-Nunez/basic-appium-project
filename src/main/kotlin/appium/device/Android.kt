package appium.device

import appium.utils.PropertiesReader
import io.appium.java_client.remote.MobileCapabilityType.AUTOMATION_NAME
import io.appium.java_client.remote.MobileCapabilityType.DEVICE_NAME
import io.appium.java_client.remote.MobileCapabilityType.APP
import io.appium.java_client.remote.MobileCapabilityType.FULL_RESET

import org.openqa.selenium.Capabilities
import org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME
import org.openqa.selenium.remote.DesiredCapabilities
import java.io.File

class Android : Platform {

    override fun getCapabilities(): Capabilities? {
        return DesiredCapabilities().apply {
            setCapability("autoGrantPermissions", true)
            setCapability("gpsEnabled", true)
            setCapability("locale", "DE")
            setCapability("language", "de")
            setCapability("newCommandTimeout", 120)
            setCapability("disableWindowAnimation", true)
            setCapability("avd","emulator2")
            setCapability(PLATFORM_NAME, "Android")
            setCapability(AUTOMATION_NAME, "UiAutomator2")
            setCapability(DEVICE_NAME, "Android Emulator")
            //setCapability(FULL_RESET, true)
            setCapability(APP, getTestAppPath())
        }
    }

    override fun getTestAppPath(): String {
        val appFilePath = File(PropertiesReader().getProp("android.app.path") as String)
        return appFilePath.absolutePath
    }
}