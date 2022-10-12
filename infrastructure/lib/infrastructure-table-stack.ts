import {aws_dynamodb as dynamodb, CfnOutput, RemovalPolicy, Stack, StackProps} from 'aws-cdk-lib';
import {Construct} from 'constructs';

export class InfrastructureTableStack extends Stack {
    constructor(scope: Construct, id: string, props?: StackProps) {
        super(scope, id, props);

        const tableName = 'Products-JVM-Example';
        const productsTable = new dynamodb.Table(this, id, {
            billingMode: dynamodb.BillingMode.PROVISIONED,
            writeCapacity: 8,
            readCapacity: 8,
            removalPolicy: RemovalPolicy.DESTROY,
            partitionKey: {name: 'id', type: dynamodb.AttributeType.STRING},
            pointInTimeRecovery: false,
            tableName: tableName,
        });

        new CfnOutput(this, `${tableName}-table-arn`, {
            value: productsTable.tableArn,
            description: `The arn of the ${tableName} table`,
            exportName: `${tableName}TableArn`,
        });
    }
}
