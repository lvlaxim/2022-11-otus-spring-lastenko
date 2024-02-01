package ru.lastenko.butterflyspringintegration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.lastenko.butterflyspringintegration.model.Butterfly;
import ru.lastenko.butterflyspringintegration.model.Caterpillar;
import ru.lastenko.butterflyspringintegration.model.Cocoon;
import ru.lastenko.butterflyspringintegration.model.Egg;

@Service
@Slf4j
public class ButterflyLifeCircleServiceImpl implements ButterflyLifeCircleService {

    @Override
    public Caterpillar hatchCaterpillarFromEgg(Egg egg) {
        String name = egg.name();
        log.info("Гусеница \"{}\" вылупилась из яйца.", name);
        return new Caterpillar(name);
    }

    @Override
    public Cocoon pupateOfCaterpillar(Caterpillar caterpillar) {
        String name = caterpillar.name();
        log.info("Гусеница \"{}\" окуклилась.", name);
        return new Cocoon(name);
    }

    @Override
    public Butterfly hatchButterflyFromCocoon(Cocoon cocoon) {
        String name = cocoon.name();
        log.info("Бабочка \"{}\" вылупилась из кокона.", name);
        return new Butterfly(name);
    }
}
