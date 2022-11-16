//package com.a203.sixback.db.replication;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import static com.a203.sixback.db.replication.ReplicationRoutingDataSource.DATASOURCE_SOURCE_KEY;
//
//@Configuration
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
//@EnableTransactionManagement
//@EnableConfigurationProperties(Replicas.class)
//@EnableJpaRepositories(basePackages = "com.a203.sixback.db.repo")
//@EnableMongoRepositories(basePackages = "com.a203.sixback.db.mongo")
//public class DatabaseConfig {
//
//    private final Replicas replicas;
//
//    public DatabaseConfig(Replicas replicas) {
//        this.replicas = replicas;
//    }
//
//    @Bean
//    @ConfigurationProperties(prefix = "spring.datasource.hikari.source")
//    public DataSource sourceDataSource() {
//        return DataSourceBuilder.create().type(HikariDataSource.class).build();
//    }
//
//    @Bean
//    public Map<String, DataSource> replicaDataSources() {
//        return replicas.replicaDataSources(HikariDataSource.class);
//    }
//
//    @Bean
//    public DataSource routingDataSource(@Qualifier("sourceDataSource") DataSource source,
//                                        @Qualifier("replicaDataSources") Map<String, DataSource> replicas) {
//        ReplicationRoutingDataSource routingDataSource = new ReplicationRoutingDataSource();
//
//        Map<Object, Object> dataSources = new HashMap<>();
//        dataSources.put(DATASOURCE_SOURCE_KEY, source);
//        dataSources.putAll(replicas);
//
//        routingDataSource.setTargetDataSources(dataSources);
//        routingDataSource.setDefaultTargetDataSource(source);
//        List<String> replicaDataSourceNames = new ArrayList<>(replicas.keySet());
//        routingDataSource.setReplicaDataSourceNames(replicaDataSourceNames);
//
//        return routingDataSource;
//    }
//
//    @Primary
//    @Bean
//    public DataSource dataSource(@Qualifier("routingDataSource") DataSource routingDataSource) {
//        return new LazyConnectionDataSourceProxy(routingDataSource);
//    }
//}
