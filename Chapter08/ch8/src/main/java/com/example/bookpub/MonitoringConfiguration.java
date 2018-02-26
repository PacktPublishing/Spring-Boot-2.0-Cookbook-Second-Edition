package com.example.bookpub;

import io.micrometer.core.instrument.Clock;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.config.NamingConvention;
import io.micrometer.graphite.GraphiteConfig;
import io.micrometer.graphite.GraphiteMeterRegistry;
import org.springframework.boot.actuate.autoconfigure.metrics.export.MetricsExporter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.Normalizer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Configuration
@ConditionalOnClass(GraphiteMeterRegistry.class)
@ConditionalOnBean(GraphiteConfig.class)
class MonitoringConfiguration {

    private static final Pattern blacklistedChars = Pattern.compile("[{}(),=\\[\\]/]");

    @Bean
    public MetricsExporter graphiteExporter(GraphiteConfig graphiteConfig, Clock clock) {
        NamingConvention namingConvention = namingConvention();
        GraphiteMeterRegistry registry = new GraphiteMeterRegistry(graphiteConfig, (id, convention) -> {
            String prefix = "bookpub.app.";
            String tags = "";

            if (id.getTags().iterator().hasNext()) {
                tags = "." + id.getConventionTags(convention).stream()
                        .map(t -> t.getKey() + "." + t.getValue())
                        .map(nameSegment -> nameSegment.replace(" ", "_"))
                        .collect(Collectors.joining("."));
            }

            return prefix + id.getConventionName(convention) + tags;
        }, clock);
        registry.config().namingConvention(namingConvention);
        registry.start();
        return () -> registry;
    }

    @Bean
    public NamingConvention namingConvention() {
        return new NamingConvention() {
            @Override
            public String name(String name, Meter.Type type, String baseUnit) {
                return format(name);
            }

            @Override
            public String tagKey(String key) {
                return format(key);
            }

            @Override
            public String tagValue(String value) {
                return format(value);
            }

            /**
             * Github Issue: https://github.com/graphite-project/graphite-web/issues/243

             * Unicode is not OK. Some special chars are not OK.
             */
            private String format(String name) {
                String sanitized = Normalizer.normalize(name, Normalizer.Form.NFKD);
                sanitized = NamingConvention.dot.tagKey(sanitized);
                return blacklistedChars.matcher(sanitized).replaceAll("_");
            }
        };
    }

    @Bean
    public JvmThreadMetrics jvmThreadMetrics() {
        return new JvmThreadMetrics();
    }
}
