package ru.vados.app1;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class App1Controller {

    private final App1Service service;

    @PostMapping("/addInfo")
    public void addInfo(@RequestBody App1Dto.App1CreateUpdateDelete info){

    }

}
