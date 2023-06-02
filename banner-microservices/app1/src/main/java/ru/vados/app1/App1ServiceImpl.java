package ru.vados.app1;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class App1ServiceImpl implements App1Service{

    private final App1Repository repository;

    public void addEntity(App1Dto.App1CreateUpdateDelete info){
        App1Entity entity = new App1Entity();
        entity.setInfo(info.getInfo());
        repository.save(entity);
    }
}
