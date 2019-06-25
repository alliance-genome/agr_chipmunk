package org.alliancegenome.agr_submission.util.aws;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.alliancegenome.agr_submission.config.ConfigHelper;
import org.alliancegenome.agr_submission.exceptions.FileSavingException;
import org.alliancegenome.agr_submission.exceptions.GenericException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

public class S3Helper {

	private Log log = LogFactory.getLog(getClass());
	private AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ConfigHelper.getAWSAccessKey(), ConfigHelper.getAWSSecretKey()))).withRegion(Regions.US_EAST_1).build();

	public int listFiles(String prefix) {
		int count = 0;
		try {
			log.info("Getting S3 file listing");
			//AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ConfigHelper.getAWSAccessKey(), ConfigHelper.getAWSSecretKey()))).withRegion(Regions.US_EAST_1).build();
			ObjectListing ol = s3.listObjects(ConfigHelper.getAWSBucketName(), prefix);
			log.debug(ol.getObjectSummaries().size());
			count = ol.getObjectSummaries().size();
			for (S3ObjectSummary summary : ol.getObjectSummaries()) {
				log.debug(" - " + summary.getKey() + "\t(size = " + summary.getSize() + ")\t(lastModified = " + summary.getLastModified() + ")");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public void saveFile(String path, File inFile) throws GenericException {
		try {
			log.info("Uploading file to S3: " + inFile.getAbsolutePath() + " -> s3://" + ConfigHelper.getAWSBucketName() + "/" + path);
			//AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ConfigHelper.getAWSAccessKey(), ConfigHelper.getAWSSecretKey()))).withRegion(Regions.US_EAST_1).build();
			TransferManager tm = TransferManagerBuilder.standard().withS3Client(s3).build();
			final Upload uploadFile = tm.upload(ConfigHelper.getAWSBucketName(), path, inFile);
			uploadFile.waitForCompletion();
			tm.shutdownNow();
			inFile.delete();
			log.info("S3 Upload complete");
		} catch (Exception e) {
			e.printStackTrace();
			throw new FileSavingException(e.getMessage());
		}
	}
	
	public List<String> getBucketObjects(String bucket) {
		//AmazonS3 s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ConfigHelper.getAWSAccessKey(), ConfigHelper.getAWSSecretKey()))).withRegion(Regions.US_EAST_1).build();
		
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
        
        return ret;
	}
	
	public void copyObject(String objectKey, String srcBucket, String dstBucket) {
		System.out.println("Copying: " + srcBucket + "/" + objectKey + " -> " + dstBucket + "/" + objectKey);
		s3.copyObject(srcBucket, objectKey, dstBucket, objectKey);
		System.out.println("Copy Complete");
	}
	
	public void deleteObject(String objectKey, String bucket) {
		System.out.println("Deleting: " + bucket + "/" + objectKey);
		s3.deleteObject(bucket, objectKey);
	}

}
