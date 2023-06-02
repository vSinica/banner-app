package ru.vados.app2;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class App2Controller {

    private final App2Service service;

    @PostMapping("/addInfo")
    public void addInfo(@RequestBody App2Dto.App2CreateUpdateDelete info){

    }

}
