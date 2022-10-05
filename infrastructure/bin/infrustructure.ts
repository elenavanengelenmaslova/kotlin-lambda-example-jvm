#!/usr/bin/env node
import 'source-map-support/register';
import {App, Stack, Tags} from 'aws-cdk-lib';
import {InfrastructureJvmStack} from '../lib/infrastructure-jvm-stack';
import {InfrastructureJvmC1Stack} from "../lib/infrastructure-jvm-c1-stack";
import {InfrastructureTableStack} from "../lib/infrastructure-table-stack";
//import PermissionsBoundaryAspect from "../lib/permission-boundary-aspect";

const app = new App();

const account_id = process.env.DEPLOY_TARGET_ACCOUNT;
const region = process.env.DEPLOY_TARGET_REGION
const environmentSettings = {account: account_id, region: region};

const stackNameTable = 'Kotlin-Lambda-JVM-table';
const stackTable = new InfrastructureTableStack(app, stackNameTable, {
  stackName: stackNameTable,
  env: environmentSettings,
  description: 'Dynamo Table used for JVM example',
});

const stackNameJVM = 'Kotlin-Lambda-JVM-example';
const stackJVM = new InfrastructureJvmStack(app, stackNameJVM, {
  stackName: stackNameJVM,
  env: environmentSettings,
  description: 'JVM example',
});

const stackNameJVMC1 = 'Kotlin-Lambda-JVM-C1-example';
const stackJVMC1 = new InfrastructureJvmC1Stack(app, stackNameJVMC1, {
  stackName: stackNameJVMC1,
  env: environmentSettings,
  description: 'JVM C1 example',
});

// const permissionBoundary = new PermissionsBoundaryAspect(
//   `arn:aws:iam::${Aws.ACCOUNT_ID}:policy/xxx-base-permissions-boundary`
// );
for (const node of app.node.children) {
  if (node instanceof Stack) {
    Tags.of(node).add('Application ID', node.stackName);
    //Aspects.of(node).add(permissionBoundary);
  }
}