package appium.utils

class CityUtils {
    companion object {
        fun getBerlinLocation(): City {
            return City(
                    latitude = 52.5161736,
                    longitude = 13.3167332,
                    altitude = 20.0
            )
        }
    }

    data class City(val latitude: Double, val longitude: Double, val altitude: Double)
}