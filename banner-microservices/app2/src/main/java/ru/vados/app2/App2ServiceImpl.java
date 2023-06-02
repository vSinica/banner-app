package ru.vados.app2;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class App2ServiceImpl implements App2Service {

    private final App1Repository repository;

    public void addEntity(App2Dto.App2CreateUpdateDelete info){
        App1Entity entity = new App1Entity();
        entity.setInfo(info.getInfo());
        repository.save(entity);
    }
}
