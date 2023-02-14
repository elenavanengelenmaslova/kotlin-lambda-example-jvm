
plugins {
    application
}

dependencies {
    implementation("software.amazon.awscdk:aws-cdk-lib:2.64.0")
    implementation("software.constructs:constructs:10.1.248")
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
