
plugins {
    application
}

dependencies {
    implementation("software.amazon.awscdk:aws-cdk-lib:2.47.0")
    implementation("software.constructs:constructs:10.1.138")
}

application {
    mainClass.set("nl.vintik.sample.infra.InfrastructureAppKt")
}

tasks.named("run") {
    dependsOn(":products:packageDistribution")
}