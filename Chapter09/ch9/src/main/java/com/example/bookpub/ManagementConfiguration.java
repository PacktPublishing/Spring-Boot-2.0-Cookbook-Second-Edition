package com.example.bookpub;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdDelegatingSerializer;
import com.fasterxml.jackson.databind.util.StdConverter;
import org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@ManagementContextConfiguration
public class ManagementConfiguration implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.
                json().
                //propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE).
                propertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE).
                //featuresToEnable(SerializationFeature.INDENT_OUTPUT).
                //featuresToEnable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY).
                build();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Set.class,
                new StdDelegatingSerializer(Set.class, new StdConverter<Set, List>() {
                    @Override
                    public List convert(Set value) {
                        LinkedList list = new LinkedList(value);
                        Collections.sort(list);
                        return list;
                    }
                })
        );
        objectMapper.registerModule(module);
        HttpMessageConverter c = new MappingJackson2HttpMessageConverter(
                objectMapper
        );
        converters.add(c);
    }
}
