package com.example.bookpubstarter.dbcount;

//import org.springframework.boot.actuate.autoconfigure.MetricsDropwizardAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.HealthIndicatorAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({DbCountAutoConfiguration.class,
        HealthIndicatorAutoConfiguration.class
        //MetricsDropwizardAutoConfiguration.class
})
@Documented
public @interface EnableDbCounting {
}
