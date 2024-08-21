package com.data.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@ComponentScan("com.data.analytics")
public class DataAnalyticsApplication implements AsyncConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(DataAnalyticsApplication.class, args);
    }

    @Override
    public Executor getAsyncExecutor() {
        return Executors.newScheduledThreadPool(8);
    }

}
