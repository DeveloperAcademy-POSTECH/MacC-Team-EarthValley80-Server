package com.ada.earthvalley.yomojomo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;

@Configuration
public class DynamoDbConfig {

	@Bean
	@Primary
	DynamoDBMapperConfig dynamoDBMapperConfig() {
		return DynamoDBMapperConfig.DEFAULT;
	}

	@Profile({"local"})
	@Bean(name = "amazonDynamoDB")
	@Primary
	public AmazonDynamoDB localAmazonDynamoDB() {
		System.out.println("Start Amazon DynamoDB Client");
		BasicAWSCredentials basicAWSCredentials =
			new BasicAWSCredentials("testAccess", "testSecret");

		return AmazonDynamoDBClientBuilder.standard()
			.withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
			.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(
				"http://localhost:8000", Regions.AP_NORTHEAST_2.getName()))
			.build();
	}

	@Bean
	@Primary
	public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB, DynamoDBMapperConfig dynamoDBMapperConfig) {
		return new DynamoDBMapper(amazonDynamoDB, dynamoDBMapperConfig);
	}

}
