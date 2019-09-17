package org.alliancegenome.agr_submission.main;

import java.util.ArrayList;
import java.util.List;

import org.alliancegenome.agr_submission.config.ConfigHelper;
import org.alliancegenome.agr_submission.util.aws.S3Helper;

public class S3Syncer {

	public static void main(String[] args) {
		ConfigHelper.init();
		boolean reportonly = false;
		
		S3Helper s3 = new S3Helper();
		
		String srcBucket = "mod-datadumps";
		String dstBucket = "mod-datadumps-dev";

		List<String> prod = s3.getBucketObjects(srcBucket);
		List<String> dev = s3.getBucketObjects(dstBucket);
		
		System.out.println("Prod size: " + prod.size());
		System.out.println("Dev size: " + dev.size());
		
		List<String> copyList = new ArrayList<String>();
		List<String> deleteList = new ArrayList<String>();
		
		for(String s: prod) {
			if(!dev.contains(s)) {
				copyList.add(s);
			}
		}
		for(String s: dev) {
			if(!prod.contains(s)) {
				deleteList.add(s);
			}
		}
		
		System.out.println("List to Copy: " + copyList.size());
		System.out.println("List to Delete: " + deleteList.size());
		
		if(!reportonly) {
			for(String s: copyList) {
				s3.copyObject(s, srcBucket, dstBucket);
			}
			
			for(String s: deleteList) {
				s3.deleteObject(s, dstBucket);
			}
		}
		
		
	}

}
