package com.example.bookpubstarter.dbcount;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.data.repository.CrudRepository;

import java.util.*;

public class DbCountMetrics implements MeterBinder {
    private Collection<CrudRepository> repositories;

    public DbCountMetrics(Collection<CrudRepository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public void bindTo(MeterRegistry registry) {
        for (CrudRepository repository : repositories) {
            String name = DbCountRunner.getRepositoryName(repository.getClass());
            String metricName = "counter.datasource." + name;
            Gauge.builder(metricName, repository, CrudRepository::count)
                    .tags("name", name)
                    .description("The number of entries in " + name + "repository")
                    .register(registry);
        }
    }

//    @Override
//    public Collection<Metric<?>> metrics() {
//        List<Metric<?>> metrics = new LinkedList<>();
//        for (CrudRepository repository : repositories) {
//            String name = DbCountRunner.getRepositoryName(repository.getClass());
//            String metricName = "counter.datasource." + name;
//            metrics.add(new Metric<>(metricName, repository.count()));
//        }
//        return metrics;
//    }

//    @Override
//    public Map<String, com.codahale.metrics.Metric> getMetrics() {
//        final Map<String, com.codahale.metrics.Metric> gauges = new HashMap<String, com.codahale.metrics.Metric>();
////        for (Metric springMetric : metrics()) {
////            gauges.put(springMetric.getName(), (Gauge<Number>) springMetric::getValue);
////        }
//        return gauges;
//    }
}
