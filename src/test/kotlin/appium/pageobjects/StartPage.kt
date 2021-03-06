package appium.pageobjects

import io.appium.java_client.pagefactory.AndroidFindBy
import org.fluentlenium.core.domain.FluentWebElement

class StartPage : AbstractScreen() {
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

    @AndroidFindBy(id = "fab")
    private lateinit var fab: FluentWebElement

    fun addBeers(amount: Int) {
        for (i in 2..amount) {
            clickOnElement(addBeerButton)
        }
    }

    fun insertNewPrice(newPrice: String) {
        price.type(newPrice)
    }

    fun getAmount() = getText(amount).toInt()

    fun getTotalPrice() = getText(totalPrice).replace("[^0-9]€".toRegex(), "")
}