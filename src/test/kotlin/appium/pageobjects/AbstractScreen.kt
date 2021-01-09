package appium.pageobjects

import io.appium.java_client.AppiumDriver
import io.appium.java_client.PerformsTouchActions
import io.appium.java_client.TouchAction
import io.appium.java_client.touch.WaitOptions
import io.appium.java_client.touch.offset.PointOption
import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.WebDriverException
import java.time.Duration
import java.util.concurrent.TimeUnit

abstract class AbstractScreen : FluentPage() {

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
                .press(PointOption.point(startX, startY))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000L)))
                .moveTo(PointOption.point(endX, endY))
                .release()
                .perform()
        } catch (ignored: WebDriverException) {
            // swipe gestures are unstable and sometimes fail with unknown reason, just retry the swipe action
        }
    }

    class PlatformTouchAction(performsTouchActions: PerformsTouchActions) :
        TouchAction<PlatformTouchAction>(performsTouchActions)

    protected fun hideKeyBoard() {
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

    protected fun clickOnElement(element: FluentWebElement) {
        waitUntilElementPresent(element)
        element.click()
    }

    protected fun getText(fluentWebElement: FluentWebElement) = fluentWebElement.text()

    protected fun waitUntilElementPresent(element: FluentWebElement) {
        await().atMost(30, TimeUnit.SECONDS).until(element).present()
    }

    protected fun waitUntilElementPresent(element: FluentWebElement, wait: Long) {
        await().atMost(wait, TimeUnit.SECONDS).until(element).present()
    }

    protected fun waitUntilElementNotPresent(element: FluentWebElement) {
        await().atMost(10, TimeUnit.SECONDS).until(element).not().present()
    }

    protected fun waitUntilElementDisplayed(element: FluentWebElement) {
        await().atMost(30, TimeUnit.SECONDS).until(element).displayed()
    }

    protected fun waitUntilElementDisplayed(element: FluentWebElement, wait: Long) {
        await().atMost(wait, TimeUnit.SECONDS).until(element).displayed()
    }

    protected fun waitUntilElementNotDisplayed(element: FluentWebElement) {
        await().atMost(10, TimeUnit.SECONDS).until(element).not().displayed()
    }

    protected fun waitUntilElementClickable(element: FluentWebElement) {
        await().atMost(30, TimeUnit.SECONDS).until(element).clickable()
    }

    protected fun waitUntilElementNotContainsText(element: FluentWebElement, text: String) {
        await().atMost(10, TimeUnit.SECONDS).until(element).text().not().contains(text)
    }

    protected fun waitUntilElementContainsText(element: FluentWebElement, text: String) {
        await().atMost(10, TimeUnit.SECONDS).until(element).text().contains(text)
    }
}

