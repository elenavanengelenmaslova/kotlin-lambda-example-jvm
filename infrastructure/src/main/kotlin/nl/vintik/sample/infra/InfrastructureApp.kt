package nl.vintik.sample.infra

import software.amazon.awscdk.App
import software.amazon.awscdk.Environment
import software.amazon.awscdk.StackProps

class InfrastructureApp {
    fun main() {
        val app = App()

        val stackNameTable = "Kotlin-Lambda-JVM-table"
        val infrastructureTableStack = InfrastructureTableStack(
            app, stackNameTable, StackProps.builder()
                .stackName(stackNameTable)
                .env(
                    Environment.builder()
                        .account(System.getenv("DEPLOY_TARGET_ACCOUNT"))
                        .region(System.getenv("DEPLOY_TARGET_REGION"))
                        .build()
                )
                .description("Dynamo Table used for JVM example")
                .build()
        )


        app.synth()
    }
}