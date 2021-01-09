package appium.device

import appium.utils.PropertiesReader
import io.appium.java_client.remote.MobileCapabilityType.APP
import io.appium.java_client.remote.MobileCapabilityType.AUTOMATION_NAME
import io.appium.java_client.remote.MobileCapabilityType.DEVICE_NAME
import org.openqa.selenium.Capabilities
import org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME
import org.openqa.selenium.remote.DesiredCapabilities

class Android : Device {

    override fun getCapabilities(): Capabilities? {
        return DesiredCapabilities().apply {
            setCapability("autoGrantPermissions", true)
            setCapability(PLATFORM_NAME, "Android")
            setCapability(AUTOMATION_NAME, "UiAutomator2")
            setCapability(DEVICE_NAME, "Android Emulator")
            setCapability(APP, getTestAppPath())
        }
    }

    override fun getTestAppPath(): String? {
        return PropertiesReader().readAppPathFromProperty("android.app.path")
    }
}