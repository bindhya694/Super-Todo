package com.superloop.api;

import org.hsqldb.util.DatabaseManagerSwing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;

/**
 * TodoTaskApplication - This class is an entry point of spring boot application
 * The following end points are accessible
 * 1.List
 * 2.Add
 * @author Bindhya Maya
 * Version 1.0
 * Created Date 01/02/2021
 */

@SpringBootApplication
@EntityScan(basePackages = "com.superloop.api.model")
@EnableJpaRepositories(basePackages = "com.superloop.api.dao")
public class TodoTaskApplication {

    private static final Logger logger = LoggerFactory.getLogger(TodoTaskApplication.class);

    /**
     * The main() method uses Spring Boot’s SpringApplication.run() method to launch
     * an application. This main() method starts up the Spring ApplicationContext
     *
     * @param args
     */
    public static void main(String[] args) {
        logger.info("--Application Is Starting Up--");
        SpringApplicationBuilder builder = new SpringApplicationBuilder(TodoTaskApplication.class);
        builder.headless(false);
        builder.run(args);
    }

    /**
     * Swing component to get the HSQL database manager run component The DB
     * explorer will open automatically when the application is up.
     */
    @PostConstruct
    public void getDbManager() {
        DatabaseManagerSwing.main(new String[]{"--url", "jdbc:hsqldb:mem:testdb", "--user", "sa", "--password", ""});
    }

    /**
     * CorsFilter for cross domain resource access
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    /**
     * default constructor
     */
    public TodoTaskApplication() {
    }
}
