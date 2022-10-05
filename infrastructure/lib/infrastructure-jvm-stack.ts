import {
  aws_dynamodb as dynamodb,
  aws_lambda as lambda,
  aws_logs as logs,
  BundlingOutput,
  DockerImage,
  Duration, Fn,
  RemovalPolicy,
  Stack,
  StackProps
} from 'aws-cdk-lib';
import {Construct} from 'constructs';
import * as os from 'os';

export class InfrastructureJvmStack extends Stack {
  constructor(scope: Construct, id: string, props?: StackProps) {
    super(scope, id, props);

    const productsTable = dynamodb.Table.fromTableArn(this, 'dynamoTable', Fn.importValue('Products-GraalVM-ExampleTableArn'));
    const graalVMNativeLambda = new lambda.Function(this, 'graalVMNativeLambdaExample', {
      description: 'Kotlin Lambda GraalVM Example',
      runtime: lambda.Runtime.PROVIDED_AL2,
      code: lambda.Code.fromAsset('../software/',
        {
          bundling: {
            image: DockerImage.fromRegistry("marksailes/al2-graalvm:17-22.2.0"),
            volumes: [{
              hostPath: os.homedir() + "/.m2/",
              containerPath: "/root/.m2/"
            }],
            user: "root",
            outputType: BundlingOutput.ARCHIVED,
            command: ["-c",
              "cd products " +
              "&& mvn clean install -P native-image "
              + "&& cp /asset-input/products/target/function.zip /asset-output/"]
          }
        }),
      handler: 'nl.vintik.sample.KotlinLambda::handleRequest',
      timeout: Duration.seconds(120),
      memorySize: 2048,
      environment: {},
      logRetention: logs.RetentionDays.ONE_DAY
    });

    productsTable.grantReadData(graalVMNativeLambda);
  }
}
