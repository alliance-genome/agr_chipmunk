package org.alliancegenome.agr_submission.util.aws;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.alliancegenome.agr_submission.config.ConfigHelper;
import org.apache.commons.codec.binary.Base64;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.BlockDeviceMapping;
import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.EbsBlockDevice;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.InstanceType;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.ResourceType;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.Tag;
import com.amazonaws.services.ec2.model.TagSpecification;
import com.amazonaws.services.ec2.model.VolumeType;

import lombok.extern.jbosslog.JBossLog;

@JBossLog
public class EC2Helper {

	public void listInstances() {
		boolean done = false;

		AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
				.withCredentials(
						new AWSStaticCredentialsProvider(new BasicAWSCredentials(ConfigHelper.getAWSAccessKey(), ConfigHelper.getAWSSecretKey())))
				.withRegion(Regions.US_EAST_1).build();

		DescribeInstancesRequest request = new DescribeInstancesRequest();
		while(!done) {
			DescribeInstancesResult response = ec2.describeInstances(request);

			for(Reservation reservation : response.getReservations()) {
				for(Instance instance : reservation.getInstances()) {
					System.out.printf("Instance: %s, AMI: %s, Type: %s, State: %s, Monitoring: %s, Name: %s\n",
							instance.getInstanceId(),
							instance.getImageId(),
							instance.getInstanceType(),
							instance.getState().getName(),
							instance.getMonitoring().getState(),
							instance.getTags().get(0).getValue()
							);
				}
			}

			request.setNextToken(response.getNextToken());

			if(response.getNextToken() == null) {
				done = true;
			}
		}
	}

	public void createInstance() {

		AmazonEC2 ec2 = AmazonEC2ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(
						new BasicAWSCredentials(ConfigHelper.getAWSAccessKey(), ConfigHelper.getAWSSecretKey())))
				.withRegion(Regions.US_EAST_1).build();

		RunInstancesRequest runInstancesRequest = new RunInstancesRequest();

		EbsBlockDevice root_ebs = new EbsBlockDevice().withVolumeSize(32).withVolumeType(VolumeType.Gp2).withDeleteOnTermination(true);
		BlockDeviceMapping root = new BlockDeviceMapping().withDeviceName("/dev/xvda").withEbs(root_ebs);
		
		EbsBlockDevice swap_ebs = new EbsBlockDevice().withVolumeSize(16).withVolumeType(VolumeType.Gp2).withDeleteOnTermination(true);
		BlockDeviceMapping swap = new BlockDeviceMapping().withDeviceName("/dev/sdb").withEbs(swap_ebs);
		
		EbsBlockDevice container_ebs = new EbsBlockDevice().withVolumeSize(200).withVolumeType(VolumeType.Gp2).withDeleteOnTermination(true);
		BlockDeviceMapping container = new BlockDeviceMapping().withDeviceName("/dev/sdc").withEbs(container_ebs);

		runInstancesRequest.withImageId("ami-08e58b93705fb503f")
		// C54xlarge   16  68  32 - $0.68  - BGI:00:10:26, EXPRESSION:00:16:01, Interactions:00:11:44, GeneDescriptions:00:12:52
		// X1e2xlarge	8  23 244 - $1.668 - BGI:00:14:13, 
		// C518xlarge  72 281 144 - $3.06  - 
		// C5d18xlarge 72 281 144 - $3.456 - BGI:00:10:43, EXPRESSION:00:15:32
		// I316xlarge  64 200 488 - $4.992 - BGI:00:13:55, EXPRESSION:00:23:53
		// R54xlarge   16  71 128 - $1.008 - BGI:00:09:33, EXPRESSION:00:17:16
		.withInstanceType(InstanceType.M4Xlarge).withMinCount(1).withMaxCount(1)
		.withBlockDeviceMappings(root, swap, container)
		.withUserData(makeUserData())
		.withTagSpecifications(getTagSpec("Docker GoCD2"))
		.withSecurityGroups("default", "ES Transport", "HTTP", "HTTPS SSL", "SSH") // Step 6 default, ES Transport, HTTP, HTTPS SSL, SSH
		.withKeyName("AGR-ssl2");

		RunInstancesResult result = ec2.runInstances(runInstancesRequest);

		log.info(result);

	}

	private TagSpecification getTagSpec(String name) {
		Tag t = new Tag("Name", name);
		List<Tag> tags = new ArrayList<Tag>();
		tags.add(t);

		TagSpecification tagspec = new TagSpecification().withResourceType(ResourceType.Instance);
		tagspec.withTags(tags);
		return tagspec;
	}

	public String makeUserData() {
		try {
			return Base64.encodeBase64String(Files.readAllBytes(Paths.get("userdata.sh")));
			//return new String(encoded, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
