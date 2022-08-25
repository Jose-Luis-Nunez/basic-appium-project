plugins {
    kotlin("jvm") version "1.7.10"
}

group = "me.josenunez"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    val fluentleniumVersion = "5.0.4"
    val appiumVersion = "8.1.1"

    implementation(
        group = "io.appium",
        name = "java-client",
        version = appiumVersion
    )
    testImplementation(
        group = "org.fluentlenium",
        name = "fluentlenium-junit-jupiter",
        version = fluentleniumVersion
    )
    implementation(
        group = "org.fluentlenium",
        name = "fluentlenium-core",
        version = fluentleniumVersion
    )
    testImplementation(
        group = "io.strikt",
        name = "strikt-core",
        version = "0.27.0"
    )
    testImplementation(
            group = "io.rest-assured",
            name = "rest-assured",
            version = "4.4.0"
    )
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
}

kotlin {
    jvmToolchain {
        (this as JavaToolchainSpec).languageVersion.set(JavaLanguageVersion.of("11"))
    }
}
