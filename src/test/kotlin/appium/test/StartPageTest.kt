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
        val expectedAmount = 4
        val expectedTotalPrice = "10,00"

        with(startPage) {
            insertNewPrice("2,50")
            addBeers(4)
        }

        expect {
            that(startPage.getAmount()).isEqualTo(expectedAmount)
            that(startPage.getTotalPrice()).isEqualTo(expectedTotalPrice)
        }
    }
}