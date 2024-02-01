package ru.lastenko.butterflyspringintegration.service;

import org.springframework.stereotype.Component;
import ru.lastenko.butterflyspringintegration.model.Egg;

import java.util.Set;

@Component
public class EggsSupplierImpl implements EggsSupplier {

    @Override
    public Iterable<Egg> getEggs() {
        return Set.of(
                new Egg("Круэлла"),
                new Egg("Мадонна"),
                new Egg("Мария")
        );
    }
}
