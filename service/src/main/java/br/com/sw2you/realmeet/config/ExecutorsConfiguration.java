package br.com.sw2you.realmeet.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExecutorsConfiguration {

    @Bean
    public Executor controllersExecutor(
        //vai carregar do arquivo application.yml.
        //caso não encontre, assume o valor padrão (:#).
        @Value("${realmeet.taskExecutor.pool.coreSize:10}") int corePoolSize,
        @Value("${realmeet.taskExecutor.pool.maxSize:20}") int maxPoolSize,
        @Value("${realmeet.taskExecutor.pool.keepAliveSeconds:50}") int keepAliveSeconds,
        @Value("${realmeet.taskExecutor.pool.keepAliveSeconds:60}") int queueCapacity
    ) {
        return new ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveSeconds,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(queueCapacity, true)
        );
    }
}
