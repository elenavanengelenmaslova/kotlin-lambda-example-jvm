# kotlin-lambda-example-jvm
Kotlin Lambda example on JVM contains two CDK stacks of Kotlin Lambda on JVM. One on C1 and one on C2 compiler.

## Build & Deployment from local machine
### Build kotlin app
```
./gradlew clean build
```
### Set up CDK deployment

Set up npm:
```
cd ${GITHUB_WORKSPACE}/infrastructure
npm install
npm run build
```

If you have not set up CDK in you AWS account yet, please run (replace variables in brackets with actual values):
```
npx cdk bootstrap aws://[aws_account_id]/[aws_region]
```

Now deploy the Kotlin/JVM app:
```
npx cdk deploy -vv --require-approval never Kotlin-Lambda-JVM-example
```

for JVM C1 example replace the above line with:
```
npx cdk deploy -vv --require-approval never Kotlin-Lambda-GraalVM-JVM-C1-example
```

## Build & Deployment to AWS account from GitHub
Set up the following secrets in your GitHub project:
```
AWS_ACCOUNT_ID
AWS_ACCESS_KEY
AWS_SECRET_KEY
```
Update AWS region in `workflow-build-deploy.yml` in `.github` folder of the project
