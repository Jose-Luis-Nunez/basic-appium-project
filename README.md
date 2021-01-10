# Basic Appium Project

This **[Kotlin](https://kotlinlang.org)** project is an example of the implementation of an [Appium](http://appium.io/) test project with [FluentLenium](https://fluentlenium.com) and [Gradle](https://gradle.org)

To run the tests locally:
1. start emulator
2. start appium server
3. run test or execute command in terminal `./gradlew clean test`
> the test fails if the emulator or the appium server is not running
## Infos
### Appium
#### Install appium
`npm install -g appium`
#### Start appium server
`appium`

### Emulator
#### Find installed emulators
`emulator -list-avds`
#### Start specific emulator
`emulator -avd [emulator name]`

## Write tests
#### Create Page Object 
The Page Object Pattern can be used to specify elements etc.
- Every page object class inherits from FluentPage()
- use @AndroidFindBy and selector to identify elements

``` java
class StartPage : FluentPage() {
    @AndroidFindBy(id = "tv_beer")
    private lateinit var itemName: FluentWebElement
    
    .
    .
    .
}
```

To instantiate a page object in a test class just use @Page:
```
@Page
lateinit var startPage: StartPage
```
#### Test example 
Every test class inherits from AppiumTestSetup()
``` java
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
        assertThat(startPage.getAmount()).isEqualTo(expectedAmount)
        assertThat(startPage.getTotalPrice()).isEqualTo(expectedTotalPrice)
    }
}

