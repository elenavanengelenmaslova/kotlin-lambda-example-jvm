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
    implementation("software.amazon.awscdk:aws-cdk-lib:2.47.0")
    implementation("software.amazon.awscdk:apigatewayv2-alpha:2.0.0-alpha.11")
    implementation("software.amazon.awscdk:apigatewayv2-integrations-alpha:2.0.0-alpha.11")
    implementation("software.constructs:constructs:10.1.138")
}

//application {
//    mainClass.set("nl.vintik.sample.infra.InfrastructureAppKt")
//}

//tasks.named("run") {
//    dependsOn(":software:shadowJar")
//}