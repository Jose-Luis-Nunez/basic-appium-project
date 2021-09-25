package appium.device

import org.openqa.selenium.Capabilities

interface Platform {
    fun getCapabilities(): Capabilities?

    fun getTestAppPath(): String?
}