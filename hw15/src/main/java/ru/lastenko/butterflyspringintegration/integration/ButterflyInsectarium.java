package ru.lastenko.butterflyspringintegration.integration;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.lastenko.butterflyspringintegration.model.Butterfly;
import ru.lastenko.butterflyspringintegration.model.Egg;

@MessagingGateway
public interface ButterflyInsectarium {

    @Gateway(requestChannel = "eggChannel", replyChannel = "butterflyChannel")
    Iterable<Butterfly> grow(Iterable<Egg> eggs);

}
