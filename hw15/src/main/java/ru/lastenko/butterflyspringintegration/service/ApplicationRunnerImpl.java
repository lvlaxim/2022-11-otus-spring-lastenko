package ru.lastenko.butterflyspringintegration.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.lastenko.butterflyspringintegration.integration.ButterflyInsectarium;

@Component
@RequiredArgsConstructor
@Slf4j
public class ApplicationRunnerImpl implements ApplicationRunner {

    private final ButterflyInsectarium butterflyInsectarium;

    private final EggsSupplier eggsSupplier;

    @Override
    public void run(ApplicationArguments args) {
        var eggs = eggsSupplier.getEggs();
        var butterflies = butterflyInsectarium.grow(eggs);
        log.info("Новые бабочки: {}", butterflies);
    }
}
