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

public class Monitor {
    PrometheusMeterRegistry registry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

    public Counter sqlErrors = Counter
            .builder("Number of SQL errors")
            .description("to keep track of how many times SQL fails us")
            .tag("purpose", "testing")
            .register(registry);

    public Timer requestLatancy = Timer
            .builder("Request Latency")
            .description("Records the internal response time to a request")
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

    public Timer getRequestLatency(){
        return requestLatancy;
    }

    public void incrementCounter(){
        sqlErrors.increment();
    }


    public PrometheusMeterRegistry getRegistry(){
        return registry;
    }


}
