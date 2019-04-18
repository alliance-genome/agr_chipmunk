package org.alliancegenome.agr_submission.main;

import org.alliancegenome.agr_submission.config.ConfigHelper;
import org.alliancegenome.agr_submission.util.aws.EC2Helper;

public class EC2Main {
	
	public static void main(String[] args) {
		ConfigHelper.init();
		
		EC2Helper ec2 = new EC2Helper();
		
		ec2.listInstances();
		ec2.createInstance();
	}

}
