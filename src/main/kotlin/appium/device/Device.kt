package appium.device

import org.openqa.selenium.Capabilities

interface Device {
    fun getCapabilities(): Capabilities?

    fun getTestAppPath(): String?
}