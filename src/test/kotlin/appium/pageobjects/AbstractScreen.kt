package appium.pageobjects

import appium.utils.CityUtils.*
import io.appium.java_client.AppiumDriver
import io.appium.java_client.PerformsTouchActions
import io.appium.java_client.TouchAction
import io.appium.java_client.touch.WaitOptions.waitOptions
import io.appium.java_client.touch.offset.PointOption.point
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.html5.Location
import java.time.Duration.ofMillis

abstract class AbstractScreen : WaitsFunctions() {

    fun swipeFromTo(from: FluentWebElement, to: FluentWebElement) {
        swipe(
                from.element.location.getX(),
                from.element.location.getY(),
                to.element.location.getX(),
                to.element.location.getY()
        )
    }

    fun swipeFromTo(from: FluentWebElement, endX: Int, endY: Int) {
        swipe(
                from.element.location.getX(),
                from.element.location.getY(),
                endX,
                endY
        )
    }

    fun swipeFromTo(startX: Int, startY: Int, to: FluentWebElement) {
        swipe(
                startX,
                startY,
                to.element.location.getX(),
                to.element.location.getY()
        )
    }

    private fun swipe(startX: Int, startY: Int, endX: Int, endY: Int) {
        try {
            PlatformTouchAction(driver as PerformsTouchActions)
                    .press(point(startX, startY))
                    .waitAction(waitOptions(ofMillis(2000L)))
                    .moveTo(point(endX, endY))
                    .release()
                    .perform()
        } catch (ignored: WebDriverException) {
            // swipe gestures are unstable and sometimes fail with unknown reason, just retry the swipe action
        }
    }

    class PlatformTouchAction(performsTouchActions: PerformsTouchActions) :
            TouchAction<PlatformTouchAction>(performsTouchActions)

    private fun hideKeyBoard() {
        (driver as AppiumDriver<*>).hideKeyboard()
    }

    fun FluentWebElement.type(text: String) {
        waitUntilElementPresent(this)
        this.clear()
        this.click()
        waitUntilElementPresent(this)
        this.write(text)
        hideKeyBoard()
    }

    fun clickOnElement(element: FluentWebElement) {
        waitUntilElementPresent(element)
        element.click()
    }
    
    fun clickOnElementWhenClickable(element: FluentWebElement) {
        waitUntilElementClickable(element)
        element.click()
    }

    fun openLink(url: String) {
        (driver as AppiumDriver<*>).get(url)
    }

    fun setDeviceLocation(city: City) {
        (driver as AppiumDriver<*>).setLocation(Location(city.latitude, city.longitude, city.altitude))
    }

    fun getText(fluentWebElement: FluentWebElement) = fluentWebElement.text()!!

    fun hideNavigationBar() {
        val example = listOf("overscan", "0,0,0,-202")
        val adbCommand = mapOf("command" to "wm", "args" to example)
        (driver as AppiumDriver<*>).executeScript("mobile: shell", adbCommand)
    }

    fun removeWhiteSpacesFromString(str: String): String {
        return str.replace("\\s".toRegex(), "")
    }
}
