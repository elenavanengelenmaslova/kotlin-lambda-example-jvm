package nl.vintik.sample.cdk

import software.amazon.awscdk.CfnOutput
import software.amazon.awscdk.CfnOutputProps
import software.amazon.awscdk.RemovalPolicy
import software.amazon.awscdk.Stack
import software.amazon.awscdk.StackProps
import software.amazon.awscdk.services.dynamodb.Attribute
import software.amazon.awscdk.services.dynamodb.AttributeType
import software.amazon.awscdk.services.dynamodb.BillingMode
import software.amazon.awscdk.services.dynamodb.Table
import software.constructs.Construct

class InfrastructureTableStack(scope: Construct, id: String, props: StackProps) : Stack(scope, id, props) {
    init {
        val tableName = "Products-JVM-Example"
        val productsTable = Table.Builder.create(this, tableName)
            .tableName(tableName)
            .partitionKey(
                Attribute.builder()
                    .type(AttributeType.STRING)
                    .name("id")
                    .build()
            )
            .removalPolicy(RemovalPolicy.DESTROY)
            .pointInTimeRecovery(false)
            .billingMode(BillingMode.PAY_PER_REQUEST)
            .build()

        CfnOutput(
            this, "${tableName}-table-arn",
            CfnOutputProps.builder()
                .value(productsTable.tableArn)
                .description("The arn of the $tableName table")
                .exportName("${tableName}TableArn").build()
        )
    }
}