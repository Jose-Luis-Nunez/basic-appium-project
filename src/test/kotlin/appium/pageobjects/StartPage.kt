package appium.pageobjects

import io.appium.java_client.pagefactory.AndroidFindBy
import org.fluentlenium.core.FluentPage
import org.fluentlenium.core.domain.FluentWebElement

class StartPage : FluentPage() {
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
        for (i in 2..amount) addBeer()
    }

    private fun addBeer() {
        addBeerButton.click()
    }

    fun insertNewPrice(newPrice: String) {
        with(price) {
            click()
            clear()
            write(newPrice)
        }
    }

    fun getAmount() = getText(amount).toInt()

    fun getTotalPrice() = getText(totalPrice).replace("[^0-9]".toRegex(), "").toInt()

    private fun getText(fluentWebElement: FluentWebElement) = fluentWebElement.text()
}