package appium.test

import appium.pageobjects.beermat.BeermatScreen
import appium.utils.AppiumTestSetup
import org.fluentlenium.assertj.FluentLeniumAssertions.assertThat
import org.fluentlenium.core.annotation.Page
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.isEqualTo

class BeermatTest : AppiumTestSetup() {

    @Page
    lateinit var beermat: BeermatScreen

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
    fun checkPriceCorrectionForSpecificAmountOfBeers() {
        val expectedAmount = 4
        val expectedTotalPrice = "10,00"

        beermat {
            insertNewPrice("2,50")
            addBeers(5)
            deleteBeers(1)
        }

        expect {
            that(beermat.getAmount()).isEqualTo(expectedAmount)
            that(beermat.getTotalPrice()).isEqualTo(expectedTotalPrice)
        }
    }

    @Test
    fun checkSnackBarUpdateForAddingBeers() {
        beermat {
            addBeers(16)
        }

        assertThat(beermat.getSnackBarText()).contains("drunk")
    }

    @Test
    fun checkSnackBarUpdateForDeletingBeers() {
        beermat {
            addBeers(2)
            deleteBeers(2)
        }

        assertThat(beermat.getSnackBarText()).contains("Booo")
    }

    @Test
    fun checkTextField() {
        beermat {
            enterDrinkName("Cola")
        }

        assertThat(beermat.getDrinkName()).contains("Cola")
    }
}
