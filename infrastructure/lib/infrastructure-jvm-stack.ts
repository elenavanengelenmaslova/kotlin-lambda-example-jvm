import {
    aws_dynamodb as dynamodb,
    aws_lambda as lambda,
    aws_logs as logs,
    Duration,
    Fn,
    Stack,
    StackProps
} from 'aws-cdk-lib';
import {Construct} from 'constructs';

export class InfrastructureJvmStack extends Stack {
    constructor(scope: Construct, id: string, props?: StackProps) {
        super(scope, id, props);

        const productsTable = dynamodb.Table.fromTableArn(this, 'dynamoTable', Fn.importValue('Products-JVM-ExampleTableArn'));
        const lambdaJvm = new lambda.Function(this, 'lambdaJvm', {
            description: 'Kotlin Lambda JVM Example',
            runtime: lambda.Runtime.JAVA_11,
            code: lambda.Code.fromAsset('../build/dist/function.zip'),
            handler: 'nl.vintik.sample.KotlinLambda::handleRequest',
            timeout: Duration.seconds(120),
            memorySize: 512,
            logRetention: logs.RetentionDays.ONE_DAY
        });

        productsTable.grantReadData(lambdaJvm);
    }
}
