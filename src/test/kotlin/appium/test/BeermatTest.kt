package appium.test

import appium.utils.AppiumTestSetup
import appium.pageobjects.Beermat
import org.fluentlenium.core.annotation.Page
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.isEqualTo
import org.fluentlenium.assertj.FluentLeniumAssertions.assertThat

class BeermatTest : AppiumTestSetup() {

    @Page
    lateinit var beermat: Beermat

    @Test
    fun checkPriceForSpecificAmountOfBeers() {
        val expectedAmount = 4
        val expectedTotalPrice = "10,00"

        beermat {
            insertNewPrice("2,50")
            addBeers(4)
        }

        expect {
            that(beermat.getAmount()).isEqualTo(expectedAmount)
            that(beermat.getTotalPrice()).isEqualTo(expectedTotalPrice)
        }
    }

    @Test
    fun checkSnackBarUpdate() {
        beermat {
            addBeers(16)
        }

       assertThat(beermat.getSnackBarText()).contains("drunk")
    }
}