buildscript {
    repositories {
        mavenCentral()
        dependencies {
            classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
        }
    }
}

plugins {
    kotlin("jvm")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("software.amazon.awssdk:dynamodb-enhanced:2.17.276")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("com.amazonaws:aws-lambda-java-core:1.2.1")
    implementation("com.amazonaws:aws-lambda-java-events:3.11.0")
    implementation("org.slf4j:slf4j-api:2.0.1")
    implementation("org.slf4j:slf4j-simple:2.0.1")
}

configurations {
    runtimeClasspath {
        exclude("org.apache.httpcomponents")
        exclude("org.jetbrains")
    }
}

tasks.register<Zip>("packageDistribution") {
    from(tasks.compileKotlin)
    from(tasks.compileJava)
    from(tasks.processResources)
    into("lib") {
        from(configurations.runtimeClasspath)
    }
    archiveFileName.set("function.zip")
    destinationDirectory.set(file("${project.rootDir}/build/dist"))
}

//tasks.test {
//    useJUnitPlatform()
//    testLogging {
//        setExceptionFormat("full")
//    }
//}
