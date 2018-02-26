package com.example.bookpub;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import com.example.bookpubstarter.dbcount.EnableDbCounting;

@SpringBootApplication
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = ManagementContextConfiguration.class))
@EnableScheduling
@EnableDbCounting
public class BookPubApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookPubApplication.class, args);
    }

    @Bean
    @Profile("logger")
    public StartupRunner schedulerRunner() {
        return new StartupRunner();
    }

//    protected final Log logger = LogFactory.getLog(getClass());
//    @Bean
//    public DbCountRunner dbCountRunner(Collection<CrudRepository> repositories) {
//        return new DbCountRunner(repositories) {
//            @Override
//            public void run(String... args) throws Exception {
//                logger.info("Manually Declared DbCountRunner");
//            }
//        };
//    }

    @Bean
    public CommandLineRunner configValuePrinter(
            @Value("${my.config.value:}") String configValue) {
        return args ->
                LogFactory.getLog(getClass()).
                        info("Value of my.config.value property is: " + configValue);
    }
}
