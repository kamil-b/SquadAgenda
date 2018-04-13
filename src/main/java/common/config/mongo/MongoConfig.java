package common.config.mongo;

import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Configuration
public class MongoConfig {

    public @Bean
    com.mongodb.reactivestreams.client.MongoClient reactiveMongoClient() {
        return MongoClients.create("mongodb://localhost");
    }

    public @Bean
    ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(reactiveMongoClient(), "mydatabase");
    }
}
