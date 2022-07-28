package org.alliancegenome.agr_submission.util.aws;

import java.io.File;
import java.util.*;

import org.alliancegenome.agr_submission.config.ConfigHelper;
import org.alliancegenome.agr_submission.exceptions.*;
import org.eclipse.microprofile.config.ConfigProvider;

import com.amazonaws.auth.*;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.*;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.*;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class S3Helper {

	public AWSCredentialsProvider getCredentials() {
		Optional<String> aws_profile = ConfigProvider.getConfig().getOptionalValue("aws.profile", String.class);
		if(aws_profile.isPresent() && aws_profile.get() != null) {
			log.info("Default AWS Profile: " + aws_profile.get());
			return new ProfileCredentialsProvider("agr");
		} else if (ConfigHelper.getAWSAccessKey() != null) {
			return new AWSStaticCredentialsProvider(new BasicAWSCredentials(ConfigHelper.getAWSAccessKey(), ConfigHelper.getAWSSecretKey()));
		} else {
			return new InstanceProfileCredentialsProvider(false);
		}
	}

	public int listFiles(String prefix) {
		int count = 0;
		try {
			log.info("Getting S3 file listing");
			AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(getCredentials()).withRegion(Regions.US_EAST_1).build();
			ObjectListing ol = s3.listObjects(ConfigHelper.getAWSBucketName(), prefix);
			log.debug(ol.getObjectSummaries().size());
			count = ol.getObjectSummaries().size();
			for (S3ObjectSummary summary : ol.getObjectSummaries()) {
				log.debug(" - " + summary.getKey() + "\t(size = " + summary.getSize() + ")\t(lastModified = " + summary.getLastModified() + ")");
			}
			s3.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return count;
	}

	public void saveFile(String path, File inFile) throws GenericException {
		try {
			log.info("Uploading file to S3: " + inFile.getAbsolutePath() + " -> s3://" + ConfigHelper.getAWSBucketName() + "/" + path);
			AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(getCredentials()).withRegion(Regions.US_EAST_1).build();
			TransferManager tm = TransferManagerBuilder.standard().withS3Client(s3).build();
			final Upload uploadFile = tm.upload(ConfigHelper.getAWSBucketName(), path, inFile);
			uploadFile.waitForCompletion();
			tm.shutdownNow();
			inFile.delete();
			log.info("S3 Upload complete");
			s3.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileSavingException(e.getMessage());
		}
	}

	public List<String> getBucketObjects(String bucket) {
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(getCredentials()).withRegion(Regions.US_EAST_1).build();

		ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucket).withMaxKeys(100);
		ListObjectsV2Result result;

		ArrayList<String> ret = new ArrayList<String>();

		do {
			result = s3.listObjectsV2(req);

			for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
				//System.out.printf(" - %s (size: %d)\n", objectSummary.getKey(), objectSummary.getSize());
				ret.add(objectSummary.getKey());
			}
			// If there are more than maxKeys keys in the bucket, get a continuation token
			// and list the next objects.
			String token = result.getNextContinuationToken();
			//System.out.println("Next Continuation Token: " + token);
			req.setContinuationToken(token);
		} while (result.isTruncated());

		s3.shutdown();
		return ret;
	}

	public void copyObject(String objectKey, String srcBucket, String dstBucket) {
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(getCredentials()).withRegion(Regions.US_EAST_1).build();
		System.out.println("Copying: " + srcBucket + "/" + objectKey + " -> " + dstBucket + "/" + objectKey);
		s3.copyObject(srcBucket, objectKey, dstBucket, objectKey);
		System.out.println("Copy Complete");
		s3.shutdown();
	}

	public void deleteObject(String objectKey, String bucket) {
		AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(getCredentials()).withRegion(Regions.US_EAST_1).build();
		System.out.println("Deleting: " + bucket + "/" + objectKey);
		s3.deleteObject(bucket, objectKey);
		s3.shutdown();
	}

}
