package edu.uniaeso.ElephantSystem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Profile("prod")
@Configuration
public class S3Config {
	
	@Autowired
	private Environment env;
	
	@Bean
	public AmazonS3 amazonS3() {
		return AmazonS3ClientBuilder.standard().withRegion(Regions.SA_EAST_1).withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(env.getProperty("AWS_ACCESS_KEY"), env.getProperty("AWS_SECRET_ACCESS"))))
				.build();
		}
	
}
