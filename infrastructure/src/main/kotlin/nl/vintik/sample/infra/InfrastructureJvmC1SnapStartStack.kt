package nl.vintik.sample.infra

import software.amazon.awscdk.Duration
import software.amazon.awscdk.Fn
import software.amazon.awscdk.Stack
import software.amazon.awscdk.StackProps
import software.amazon.awscdk.services.dynamodb.Table
import software.amazon.awscdk.services.lambda.*
import software.amazon.awscdk.services.lambda.Function
import software.amazon.awscdk.services.logs.RetentionDays
import software.constructs.Construct

class InfrastructureJvmC1SnapStartStack(scope: Construct, id: String, props: StackProps) : Stack(scope, id, props) {
    init {
        val productsTable = Table.fromTableArn(this, "dynamoTable", Fn.importValue("Products-JVM-ExampleTableArn"))
        val function = Function.Builder.create(this, "lambdaJvmC1SnapStart")
            .description("Kotlin Lambda JVM C1 SnapStart Example")
            .handler("nl.vintik.sample.KotlinLambda::handleRequest")
            .runtime(Runtime.JAVA_11)
            .code(Code.fromAsset("../build/dist/function.zip"))
            .environment(
                mapOf(
                    "JAVA_TOOL_OPTIONS" to "-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
                )
            )
            .logRetention(RetentionDays.ONE_WEEK)
            .memorySize(512)
            .timeout(Duration.seconds(120))
            .build()

        //enable SnapStart
        (function.node.defaultChild as CfnFunction).setSnapStart(
            CfnFunction.SnapStartProperty.builder().applyOn("PublishedVersions").build()
        )
        // publish a version
        Version(this, "SnapStartVersion") { function }

        productsTable.grantReadData(function)
    }
}