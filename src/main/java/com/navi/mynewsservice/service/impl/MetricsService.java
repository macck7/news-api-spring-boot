package com.navi.mynewsservice.service.impl;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MetricsService {

    private final CollectorRegistry collectorRegistry;
    Map<String, Counter> counterNameWithCounter = new HashMap<>();
    Map<String, Histogram> histogramNameWithHistogram = new HashMap<>();
    Map<String, Map<String, Counter>> counterNameAndStatusCode = new HashMap<>();

    @Autowired
    public MetricsService(CollectorRegistry collectorRegistry) {
        this.collectorRegistry = collectorRegistry;
    }

    public Counter getCounterWithName(String name) {
        return counterNameWithCounter.computeIfAbsent(name, k -> Counter.build().name(name)
                .labelNames("api")
                .help(name).register(collectorRegistry));
    }
    public Counter getCounterForTopHeadlines(String name, String statusCode) {
        return counterNameWithCounter.computeIfAbsent(name, k -> Counter.build().name(name)
                .labelNames("category", "country", "status_code")
                .help("Total number of calls to get headlines for a pair of category and country")
                .register(collectorRegistry));
    }


    public Counter getCounterWithNameAndStatusCodes(String name) {
        if (!counterNameAndStatusCode.containsKey(name)) {
            counterNameAndStatusCode.put(name, new HashMap<>());
        }

        return counterNameAndStatusCode.get(name).computeIfAbsent(name, k -> Counter.build()
                .name(name )
                .labelNames("api_name", "status_code")
                .help(name )
                .register(collectorRegistry));
    }

    public Histogram getHistogramWithName(String name) {
        return histogramNameWithHistogram.computeIfAbsent(name, k -> Histogram.build().name(name)
                .labelNames("api")
                .help(name).register(collectorRegistry));
    }
}

