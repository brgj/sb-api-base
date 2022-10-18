package com.bradgjohnson.restservice.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class AppConfiguration {
    @Bean
    public MongoClient mongoClient(@Value("${mongodb.host}") String host,
                                   @Value("${mongodb.port}") Integer port,
                                   @Value("${mongodb.user}") String user,
                                   @Value("${mongodb.password}") String password) {
        var credential = MongoCredential.createCredential(user, "admin", password.toCharArray());

        return MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(Arrays.asList(new ServerAddress(host, port))))
                        .credential(credential)
                        .build());
    }

    @Bean
    public MongoDatabase mongoDatabase(MongoClient mongoClient, @Value("${mongodb.database}") String database) {
        CodecRegistry defaultCodecRegistry = MongoClientSettings.getDefaultCodecRegistry();
        CodecRegistry fromProvider = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(defaultCodecRegistry, fromProvider);
        return mongoClient.getDatabase(database).withCodecRegistry(pojoCodecRegistry);
    }
}
