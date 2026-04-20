plugins {
    java
    scala
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.scala.library)

    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(libs.versions.java.get().toInt())
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
