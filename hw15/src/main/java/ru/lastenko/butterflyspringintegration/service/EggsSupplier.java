package ru.lastenko.butterflyspringintegration.service;

import ru.lastenko.butterflyspringintegration.model.Egg;

public interface EggsSupplier {
    Iterable<Egg> getEggs();
}
