package com.navi.mynewsservice.service.impl;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MetricsService {

    private final CollectorRegistry collectorRegistry;
    Map<String, Counter> counterNameWithCounter = new HashMap<>();

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

    private final Map<String, Map<String, Counter>> counterNameAndStatusCode = new HashMap<>();

    public Counter getCounterWithNameAndStatusCodes(String name, String statusCode) {
        if (!counterNameAndStatusCode.containsKey(name)) {
            counterNameAndStatusCode.put(name, new HashMap<>());
        }

        return counterNameAndStatusCode.get(name).computeIfAbsent(statusCode, k -> Counter.build()
                .name(name + "_status_code")
                .labelNames("api_name", "status_code")
                .help(name + " - Status Code")
                .register(collectorRegistry));
    }
}

