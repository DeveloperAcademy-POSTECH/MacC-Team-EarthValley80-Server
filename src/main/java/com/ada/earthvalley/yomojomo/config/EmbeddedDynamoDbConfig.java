package com.ada.earthvalley.yomojomo.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;

@Profile("local")
@Configuration
@ConditionalOnProperty(name = "embedded-dynamodb.use", havingValue = "true")
public class EmbeddedDynamoDbConfig {
	private DynamoDBProxyServer server;

	@PostConstruct
	public void start() {
		if (server != null) {
			return;
		}
		try {
			AwsDynamoDbLocalTestUtils.initSqLite();
			server = ServerRunner.createServerFromCommandLineArgs(new String[] {"-inMemory"});
			server.start();
			System.out.println("start Embedded DynamoDB");
		} catch (Exception e) {
			throw new IllegalStateException("Fail Start Embedded DynamoDB", e);
		}
	}

	@PreDestroy
	public void stop() {
		if (server != null) {
			return;
		}
		try {
			System.out.println("Stop embedded DynamoDB");
			server.stop();
		} catch (Exception e) {
			throw new IllegalStateException("Fail Stop Embedded DynamoDB", e);
		}
	}
}
