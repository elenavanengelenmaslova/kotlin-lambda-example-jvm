package nl.vintik.sample.infra

import software.amazon.awscdk.App
import software.amazon.awscdk.Environment
import software.amazon.awscdk.StackProps

fun main() {
    val app = App()

    val environment = Environment.builder()
        .account(System.getenv("DEPLOY_TARGET_ACCOUNT"))
        .region(System.getenv("DEPLOY_TARGET_REGION"))
        .build()

    val stackNameTable = "Kotlin-Lambda-JVM-table"
    InfrastructureTableStack(
        app, stackNameTable, StackProps.builder()
            .stackName(stackNameTable)
            .env(environment)
            .description("Dynamo Table used for JVM example")
            .build()
    )

    val stackNameJVM = "Kotlin-Lambda-JVM-example"
    InfrastructureJvmStack(
        app, stackNameJVM,
        StackProps.builder()
            .stackName(stackNameJVM)
            .env(environment)
            .description("JVM example")
            .build()
    )

    val stackNameJVMArm64 = "Kotlin-Lambda-JVM-Arm64-example"
    InfrastructureJvmArm64Stack(
        app, stackNameJVMArm64,
        StackProps.builder()
            .stackName(stackNameJVMArm64)
            .env(environment)
            .description("JVM Arm64 example")
            .build()
    )

    val stackNameJVMC1 = "Kotlin-Lambda-JVM-C1-example"
    InfrastructureJvmC1Stack(
        app, stackNameJVMC1,
        StackProps.builder()
            .stackName(stackNameJVMC1)
            .env(environment)
            .description("JVM C1 example")
            .build()
    )

    val stackNameJVMC1Arm64 = "Kotlin-Lambda-JVM-C1-Arm64-example"
    InfrastructureJvmC1Arm64Stack(
        app,
        stackNameJVMC1Arm64,
        StackProps.builder()
            .stackName(stackNameJVMC1Arm64)
            .env(environment)
            .description("JVM C1 Arm64 example")
            .build()
    )

    val stackNameJVMSnapStart = "Kotlin-Lambda-JVM-SnapStart-example"
    InfrastructureJvmSnapStartStack(
        app,
        stackNameJVMSnapStart,
        StackProps.builder()
            .stackName(stackNameJVMSnapStart)
            .env(environment)
            .description("JVM SnapStart example")
            .build()
    )

    val stackNameJVMC1SnapStart = "Kotlin-Lambda-JVM-C1-SnapStart-example"
    InfrastructureJvmSnapStartStack(
        app,
        stackNameJVMC1SnapStart,
        StackProps.builder()
            .stackName(stackNameJVMC1SnapStart)
            .env(environment)
            .description("JVM C1 SnapStart example")
            .build()
    )
    app.synth()
}
