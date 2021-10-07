package appium.test

import appium.pageobjects.BeermatScreen
import appium.utils.AppiumTestSetup
import io.appium.java_client.service.local.AppiumDriverLocalService
import io.appium.java_client.service.local.AppiumServiceBuilder
import org.fluentlenium.assertj.FluentLeniumAssertions.assertThat
import org.fluentlenium.core.annotation.Page
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.jupiter.api.Test
import strikt.api.expect
import strikt.assertions.isEqualTo
import java.io.File
import java.util.*


class BeermatTest : AppiumTestSetup() {
    private var service: AppiumDriverLocalService? = null

    @BeforeClass
    fun startAppiumServer() {
        val builder = AppiumServiceBuilder()
        builder.usingAnyFreePort()
        // Tell builder where node is installed. Or set this path in an environment variable named NODE_PATH
        builder.usingDriverExecutable(File("path_to_node"))
        // Tell builder where Appium is installed. Or set this path in an environment variable named APPIUM_PATH
        builder.withAppiumJS(File("appium"))
        val environment:HashMap<String,String> = HashMap<String,String>()
        environment["PATH"] = "/usr/local/bin:" + System.getenv("PATH")
        builder.withEnvironment(environment)
        service = AppiumDriverLocalService.buildService(builder)
        service?.start()
    }
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

    @AfterClass
    fun stopAppiumServer() {
        service?.stop()
    }
}