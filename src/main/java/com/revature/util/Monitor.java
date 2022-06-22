package com.revature.util;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.DiskSpaceMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.binder.system.UptimeMetrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

import java.io.File;
import java.time.Duration;

public class Monitor {
    PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

    public Counter sqlErrors = Counter
            .builder("SQL errors count")
            .description("to keep track of how many times SQL fails us")
            .tag("purpose", "testing")
            .register(registry);

    public Counter internalServerErrors = Counter
            .builder("internal server error count")
            .description("Increments whenever a 500 error code is returned")
            .tag("purpose", "testing")
            .register(registry);

    public Timer requestLatency = Timer
            .builder("Request Latency")
            .sla(Duration.ofMillis(1000), Duration.ofMillis(500), Duration.ofMillis(250))
            .publishPercentiles(0.5, 0.9, 0.99)
            .description("Records the internal response time to a request")
            .tag("purpose", "metrics")
            .register(registry);


    public Timer dbRequestLatency = Timer
            .builder("Database Request Latency")
            .sla(Duration.ofMillis(3000), Duration.ofMillis(1500), Duration.ofMillis(500))
            .publishPercentiles(0.5, 0.9, 0.99)
            .description("Records the internal database response time to a request")
            .tag("purpose", "metrics")
            .register(registry);

    public Monitor(){
        new ClassLoaderMetrics().bindTo(registry);
        new JvmMemoryMetrics().bindTo(registry);
        new JvmGcMetrics().bindTo(registry);
        new JvmThreadMetrics().bindTo(registry);
        new UptimeMetrics().bindTo(registry);
        new ProcessorMetrics().bindTo(registry);
        new DiskSpaceMetrics(new File(System.getProperty("user.dir"))).bindTo(registry);
    }

    public Timer getDbRequestLatency(){
        return dbRequestLatency;
    }

    public Timer getRequestLatency(){return requestLatency;}

    public void incrementSqlCounter(){
        sqlErrors.increment();
    }

    public void incrementServorError(){
        internalServerErrors.increment();
    }


    public PrometheusMeterRegistry getRegistry(){
        return registry;
    }


}
