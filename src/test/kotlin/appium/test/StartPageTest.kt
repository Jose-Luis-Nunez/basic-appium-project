package appium.test

import appium.utils.AppiumTestSetup
import appium.pageobjects.StartPage
import org.fluentlenium.core.annotation.Page
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.isEqualTo


class StartPageTest : AppiumTestSetup() {

    @Page
    lateinit var startPage: StartPage

    @Test
    fun checkPriceForSpecificAmountOfBeers() {
        val expectedAmount = 5
        val expectedTotalPrice = 995

        with(startPage) {
            insertNewPrice("1.99")
            addBeers(5)
        }

        expect {
            that(startPage.getAmount()).isEqualTo(expectedAmount)
            that(startPage.getTotalPrice()).isEqualTo(expectedTotalPrice)
        }
    }
}