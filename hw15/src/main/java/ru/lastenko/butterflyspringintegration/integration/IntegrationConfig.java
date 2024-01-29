package ru.lastenko.butterflyspringintegration.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import ru.lastenko.butterflyspringintegration.service.ButterflyLifeCircleService;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannel eggChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel caterpillarChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel cocoonChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel butterflyChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow eggsFlow(ButterflyLifeCircleService butterflyLifeCircleService) {
        return IntegrationFlow.from(eggChannel())
                .split()
                .handle(butterflyLifeCircleService, "hatchCaterpillarFromEgg")
                .channel(caterpillarChannel())
                .get();
    }

    @Bean
    public IntegrationFlow caterpillarFlow(ButterflyLifeCircleService butterflyLifeCircleService) {
        return IntegrationFlow.from(caterpillarChannel())
                .handle(butterflyLifeCircleService, "pupateOfCaterpillar")
                .channel(cocoonChannel())
                .get();
    }

    @Bean
    public IntegrationFlow cocoonFlow(ButterflyLifeCircleService butterflyLifeCircleService) {
        return IntegrationFlow.from(cocoonChannel())
                .handle(butterflyLifeCircleService, "hatchButterflyFromCocoon")
                .aggregate()
                .channel(butterflyChannel())
                .get();
    }

}
