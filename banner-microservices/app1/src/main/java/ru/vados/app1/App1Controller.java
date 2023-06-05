package ru.vados.app1;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
public class App1Controller {

    private final App1Service service;

    @GetMapping("/addInfo")
    public ResponseEntity<Map<String, Object>> all(){
        System.out.println("----------");
            Map<String, Object> user1 = new HashMap();
            user1.put("id", 1);
            user1.put("name", "Joel");

            Map<String, Object> user2 = new HashMap();
            user2.put("id", 2);
            user2.put("name", "Narasimha");

            Map<String, Object> users = new HashMap<>();
            users.put("1", user1);
            users.put("2", user2);

            return ResponseEntity.ok(users);
    }

}
