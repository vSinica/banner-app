package ru.vados.app2;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class App2Controller {

    private final App2Service service;

    @GetMapping("/getInfo")
    public ResponseEntity<Map<String, Object>> addInfo(){
        Map<String, Object> user1 = new HashMap();
        user1.put("name", "Joel");

        return ResponseEntity.ok(user1);
    }

}
