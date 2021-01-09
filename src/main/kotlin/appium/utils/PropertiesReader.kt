package appium.utils

import java.io.File
import java.util.Properties

class PropertiesReader {

    @Suppress("UNCHECKED_CAST")
    fun <T> getProp(key: String): T {
        val props = javaClass.classLoader.getResourceAsStream("config.properties").use {
            Properties().apply { load(it) }
        }
        return (props.getProperty(key) as T) ?: throw RuntimeException("could not find property $key")
    }

    fun readAppPathFromProperty(property: String): String {
        val androidAppPath = PropertiesReader().getProp(property) ?: "unsupported"
        val file = File(androidAppPath)
        return file.absolutePath
    }

    fun getAppiumServerUrl(): String? = PropertiesReader().getProp("appium.server.url")

}