
plugins {
    application
}

dependencies {
    implementation("software.amazon.awscdk:aws-cdk-lib:2.53.0")
    implementation("software.constructs:constructs:10.1.173")
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
