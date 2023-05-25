package com.example.gyublog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class MongodbConfig extends AbstractMongoClientConfiguration {
    @Value("${spring.data.mongodb.database}")
    private String databaseName;
    @Value("${spring.data.mongodb.uri}")
    private String uri;
    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public MongoDatabaseFactory mongoDbFactory() {
        return new SimpleMongoClientDatabaseFactory(uri);
    }

    @Override
    public MongoTemplate mongoTemplate(MongoDatabaseFactory databaseFactory, MappingMongoConverter converter) {
        DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
        MongoMappingContext mongoMappingContext = new MongoMappingContext();
        MappingMongoConverter mappingMongoConverter = new MappingMongoConverter(dbRefResolver, mongoMappingContext);
        mappingMongoConverter.setTypeMapper(null);
        mappingMongoConverter.afterPropertiesSet();
        return new MongoTemplate(mongoDbFactory(), mappingMongoConverter);
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        return new MongoTransactionManager(mongoDbFactory());
    }
}
