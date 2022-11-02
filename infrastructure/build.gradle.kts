
plugins {
    application
}

dependencies {
    implementation("software.amazon.awscdk:aws-cdk-lib:2.47.0")
    implementation("software.amazon.awscdk:apigatewayv2-alpha:2.0.0-alpha.11")
    implementation("software.amazon.awscdk:apigatewayv2-integrations-alpha:2.0.0-alpha.11")
    implementation("software.constructs:constructs:10.1.138")
}

application {
    mainClass.set("nl.vintik.sample.infra.InfrastructureApp")
}

tasks.named("run") {
    dependsOn(":products:packageDistribution")
}