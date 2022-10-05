import {CfnResource, IAspect} from "aws-cdk-lib";
import {IConstruct} from "constructs";


export default class PermissionsBoundaryAspect
  implements IAspect {
  
  private readonly permissionsBoundaryArn: string;
  
  constructor(permissionBoundaryArn: string) {
    this.permissionsBoundaryArn = permissionBoundaryArn;
  }
  
  public visit(node: IConstruct): void {
    if (CfnResource.isCfnResource(node) && node.cfnResourceType === 'AWS::IAM::Role') {
      node.addPropertyOverride('PermissionsBoundary', this.permissionsBoundaryArn);
    }
  }
  
}