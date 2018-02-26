package com.example.bookpub;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

public class ApacheCommonsConfigurationEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            ApacheCommonsConfigurationPropertySource.addToEnvironment(environment,
                    new XMLConfiguration("commons-config.xml"));
        } catch (ConfigurationException e) {
            throw new RuntimeException("Unable to load commons-config.xml", e);
        }
    }
}
