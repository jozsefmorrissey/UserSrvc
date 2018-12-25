package com.aboutMe.server;

import java.util.Properties;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.aboutMe.server.utils.GenUtils;

@SpringBootApplication
public class ServerApplication {

	private static String token;
	
    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        String password = GenUtils.getSalt(token);
        config.setPassword(password);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
	
	public static void main(String[] args) {
		if (args.length > 0) {
			token = args[0];
			System.out.println("Token: " + token);
		}
		
        SpringApplication application = new SpringApplication(ServerApplication.class);

        Properties properties = new Properties();
        String dbPassEnc = GenUtils.getPassword("dbPass", token);
        properties.put("spring.datasource.password", "ENC(" + dbPassEnc + ")");
        application.setDefaultProperties(properties);
		
        application.run(args);
	}
}
