import {
  aws_dynamodb as dynamodb,
  aws_lambda as lambda,
  aws_logs as logs, CfnOutput,
  Duration,
  Fn,
  Stack,
  StackProps
} from 'aws-cdk-lib';
import {Construct} from 'constructs';

export class InfrastructureJvmC1Stack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const productsTable = dynamodb.Table.fromTableArn(this, 'dynamoTable', Fn.importValue('Products-JVM-ExampleTableArn'));
    const lambdaJvmC1 = new lambda.Function(this, 'lambdaJvmC1', {
      description: 'Kotlin Lambda JVM C1 Example',
      runtime: lambda.Runtime.JAVA_11,
      code: lambda.Code.fromAsset('../build/dist/function.zip'),
      handler: 'nl.vintik.sample.KotlinLambda::handleRequest',
      timeout: Duration.seconds(120),
      memorySize: 512,
      environment: {
        JAVA_TOOL_OPTIONS: "-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
      },
      logRetention: logs.RetentionDays.ONE_DAY
    });

    productsTable.grantReadData(lambdaJvmC1);

    new CfnOutput(this, `lambdaJvmC1-fn-arn`, {
      value: lambdaJvmC1.functionArn,
      description: `The arn of the lambdaJvmC1 function`,
      exportName: `lambdaJvmC1FnArn`,
    });
  }
}
