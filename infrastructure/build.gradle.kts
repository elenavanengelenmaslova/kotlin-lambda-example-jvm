
plugins {
    application
}

dependencies {
    // https://mvnrepository.com/artifact/software.amazon.awscdk/aws-cdk-lib
    implementation("software.amazon.awscdk:aws-cdk-lib:2.121.1")
    // https://mvnrepository.com/artifact/software.constructs/constructs
    implementation("software.constructs:constructs:10.3.0")
}

application {
    mainClass.set("nl.vintik.sample.infra.InfrastructureAppKt")
}

tasks.named("run") {
    dependsOn(":products:packageDistribution")
}
repositories {
    mavenCentral()
}
