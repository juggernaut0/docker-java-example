plugins {
    kotlin("jvm") version "1.7.10"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    val dockerJavaVersion = "3.2.13"
    implementation("com.github.docker-java:docker-java-core:$dockerJavaVersion")
    implementation("com.github.docker-java:docker-java-transport-zerodep:$dockerJavaVersion")
}

application {
    mainClass.set("dockerjavaexample.MainKt")
}
