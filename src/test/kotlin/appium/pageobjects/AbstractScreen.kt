package appium.pageobjects

import appium.pageobjects.AbstractScreen.Direction.*
import io.appium.java_client.AppiumDriver
import io.appium.java_client.PerformsTouchActions
import io.appium.java_client.TouchAction
import io.appium.java_client.touch.WaitOptions.waitOptions
import io.appium.java_client.touch.offset.PointOption
import io.appium.java_client.touch.offset.PointOption.point
import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.domain.FluentWebElement
import org.openqa.selenium.WebDriverException
import java.time.Duration.ofMillis
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

    protected fun getText(fluentWebElement: FluentWebElement) = fluentWebElement.text()!!

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

    protected fun swipeScreen(direction: Direction) {
        println("swipeScreen(): dir: '$direction'") // always log your actions

        val animationTime = 200L // ms
        val edgeBorder = 10 // better avoid edges
        val pointOptionStart: PointOption<*>
        val pointOptionEnd: PointOption<*>

        // init screen variables
        val dimension = driver.manage().window().size

        // init start point = center of screen
        pointOptionStart = point(dimension.width / 2, dimension.height / 2)
        pointOptionEnd = when (direction) {
            DOWN -> point(dimension.width / 2, dimension.height - edgeBorder)
            UP -> point(dimension.width / 2, edgeBorder)
            LEFT -> point(edgeBorder, dimension.height / 2)
            RIGHT -> point(dimension.width - edgeBorder, dimension.height / 2)
        }
        // execute swipe using TouchAction
        try {
            PlatformTouchAction(driver as PerformsTouchActions)
                    .press(pointOptionStart)
                    .waitAction(waitOptions(ofMillis(2000L)))
                    .moveTo(pointOptionEnd)
                    .release().perform()
        } catch (e: Exception) {
            System.err.println("swipeScreen(): TouchAction FAILED${e.message}")
            return
        }
        // always allow swipe action to complete
        try {
            Thread.sleep(animationTime)
        } catch (e: InterruptedException) {
        }
    }

    enum class Direction {
        UP, DOWN, LEFT, RIGHT
    }
}

