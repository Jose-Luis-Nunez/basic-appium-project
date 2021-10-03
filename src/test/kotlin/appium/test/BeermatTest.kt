package appium.test

import appium.utils.AppiumTestSetup
import appium.pageobjects.BeermatScreen
import org.fluentlenium.core.annotation.Page
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.isEqualTo
import org.fluentlenium.assertj.FluentLeniumAssertions.assertThat

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
    fun checkMaxSnackBarUpdateForAddingBeers() {
        beermat {
            addBeers(37)
        }

        assertThat(beermat.getSnackBarText()).contains("cirrhosis")
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

    @Test
    fun checkTextFieldForFood() {
        beermat {
            enterDrinkName("Doener")
        }

        assertThat(beermat.getDrinkName()).contains("Doener")
    }

    @Test
    fun checkTextFieldForFood2() {
        beermat {
            enterDrinkName("Doener")
        }

        assertThat(beermat.getDrinkName()).contains("Doener")
    }
}