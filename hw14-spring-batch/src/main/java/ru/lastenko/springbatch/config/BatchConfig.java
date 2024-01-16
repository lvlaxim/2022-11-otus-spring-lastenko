package ru.lastenko.springbatch.config;

import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.lastenko.springbatch.registry.IdMigrationRegistry;
import ru.lastenko.springbatch.registry.InMemoryIdMigrationRegistry;

@SuppressWarnings("unused")
@Configuration
public class BatchConfig {

    @Bean
    public JobRegistryBeanPostProcessor postProcessor(JobRegistry jobRegistry) {
        var processor = new JobRegistryBeanPostProcessor();
        processor.setJobRegistry(jobRegistry);
        return processor;
    }

}
