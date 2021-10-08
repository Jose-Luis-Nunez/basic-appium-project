package appium.pageobjects

import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.domain.FluentWebElement
import java.util.concurrent.TimeUnit

abstract class WaitsFunctions: FluentPage() {

    fun waitUntilElementPresent(element: FluentWebElement) {
        await().atMost(30, TimeUnit.SECONDS).until(element).present()
    }

    fun waitUntilElementPresent(element: FluentWebElement, wait: Long) {
        await().atMost(wait, TimeUnit.SECONDS).until(element).present()
    }

    fun waitUntilElementNotPresent(element: FluentWebElement) {
        await().atMost(10, TimeUnit.SECONDS).until(element).not().present()
    }

    fun waitUntilElementDisplayed(element: FluentWebElement) {
        await().atMost(30, TimeUnit.SECONDS).until(element).displayed()
    }

    fun waitUntilElementDisplayed(element: FluentWebElement, wait: Long) {
        await().atMost(wait, TimeUnit.SECONDS).until(element).displayed()
    }

    fun waitUntilElementNotDisplayed(element: FluentWebElement) {
        await().atMost(10, TimeUnit.SECONDS).until(element).not().displayed()
    }

    fun waitUntilElementClickable(element: FluentWebElement) {
        await().atMost(30, TimeUnit.SECONDS).until(element).clickable()
    }

    fun waitUntilElementNotContainsText(element: FluentWebElement, text: String) {
        await().atMost(10, TimeUnit.SECONDS).until(element).text().not().contains(text)
    }

    fun waitUntilElementContainsText(element: FluentWebElement, text: String) {
        await().atMost(10, TimeUnit.SECONDS).until(element).text().contains(text)
    }
}