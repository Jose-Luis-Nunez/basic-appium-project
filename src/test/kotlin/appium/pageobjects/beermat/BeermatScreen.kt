package appium.pageobjects.beermat

import appium.pageobjects.AbstractScreen
import io.appium.java_client.pagefactory.AndroidFindBy
import org.fluentlenium.core.domain.FluentWebElement

private const val START_ADD_BEERS = 2
private const val START_DELETE_BEERS = 1

class BeermatScreen : AbstractScreen() {
    @AndroidFindBy(id = "tv_beer")
    private lateinit var itemName: FluentWebElement

    @AndroidFindBy(id = "tv_beer_count")
    private lateinit var amount: FluentWebElement

    @AndroidFindBy(id = "button_add")
    private lateinit var addBeerButton: FluentWebElement

    @AndroidFindBy(id = "button_reduce")
    private lateinit var removeBeerButton: FluentWebElement

    @AndroidFindBy(id = "et_price")
    private lateinit var price: FluentWebElement

    @AndroidFindBy(id = "tv_total_price_of_line")
    private lateinit var totalPrice: FluentWebElement

    @AndroidFindBy(id = "snackbar_text")
    private lateinit var snackBar: FluentWebElement

    fun addBeers(numberOfBeers: Int) {
        for (i in 2..numberOfBeers) {
            clickOnElement(addBeerButton)
        }
    }

    fun deleteBeers(numberOfBeers: Int) {
        loop(START_DELETE_BEERS, numberOfBeers)
    }

    private fun loop(start: Int, numberOfBeers: Int) {
        for (i in 1..numberOfBeers) {
            clickOnElement(removeBeerButton)
        }
    }

    fun insertNewPrice(newPrice: String) = price.type(newPrice)

    fun enterDrinkName(drink: String) = itemName.type(drink)

    fun getAmount() = getText(amount).toInt()

    fun getSnackBarText() = getText(snackBar)

    fun getDrinkName() = getText(itemName)

    fun getTotalPrice() = getText(totalPrice).replace("[^0-9]€".toRegex(), "")
}
