import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.20-M1"
}

group = "me.josenunez"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    val fluentleniumVersion = "4.5.1"
    val seleniumVersion = "3.141.59"
    val assertjVersion = "3.6.1"
    val appiumVersion = "7.4.1"
    val jUnitVersion = "5.5.2"

    testImplementation(
        group = "org.junit.jupiter",
        name = "junit-jupiter",
        version = jUnitVersion
    )
    testImplementation(
        group = "org.assertj",
        name = "assertj-core",
        version = assertjVersion
    )
    implementation(
        group = "io.appium",
        name = "java-client",
        version = appiumVersion
    )
    implementation(
        group = "org.seleniumhq.selenium",
        name = "selenium-java",
        version = seleniumVersion
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
    implementation(
        group = "org.fluentlenium",
        name = "fluentlenium-assertj",
        version = fluentleniumVersion
    )
    testImplementation(
        group = "io.strikt",
        name = "strikt-core",
        version = "0.27.0"
    )
    testImplementation(
            group = "org.jetbrains.kotlin",
            name = "kotlin-reflect",
            version = "1.4.20-M1"
    )
    testImplementation(
            group = "io.rest-assured",
            name = "rest-assured",
            version = "4.4.0"
    )
    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}