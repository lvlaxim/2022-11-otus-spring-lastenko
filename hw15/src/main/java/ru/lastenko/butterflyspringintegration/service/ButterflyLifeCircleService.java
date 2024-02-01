package ru.lastenko.butterflyspringintegration.service;

import ru.lastenko.butterflyspringintegration.model.Butterfly;
import ru.lastenko.butterflyspringintegration.model.Caterpillar;
import ru.lastenko.butterflyspringintegration.model.Cocoon;
import ru.lastenko.butterflyspringintegration.model.Egg;

public interface ButterflyLifeCircleService {

    Caterpillar hatchCaterpillarFromEgg(Egg egg);

    Cocoon pupateOfCaterpillar(Caterpillar caterpillar);

    Butterfly hatchButterflyFromCocoon(Cocoon cocoon);
}
