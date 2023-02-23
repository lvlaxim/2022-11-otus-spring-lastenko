package ru.lastenko.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.library.domain.Identifiable;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IdentifiableServiceImpl implements IdentifiableService {

    private final IOService ioService;

    @Override
    public <T extends Identifiable> T selectByIdFrom(Collection<T> entities) {
        ioService.outputString(entities.toString());
        Optional<T> entity = Optional.empty();
        while (entity.isEmpty()) {
            ioService.outputString("введите id-номер из списка выше");
            String idAsStr = ioService.readString();
            long id;
            try {
                id = Long.parseLong(idAsStr);
            } catch (NumberFormatException e) {
                ioService.outputString("введно некорректное значение");
                continue;
            }
            entity = entities.stream()
                    .filter(e -> ((Long) e.getId()).equals(id))
                    .findFirst();
        }
        return entity.get();
    }
}
