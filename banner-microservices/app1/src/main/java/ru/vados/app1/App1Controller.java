package ru.vados.app1;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@RestController
@AllArgsConstructor
public class App1Controller {



    @GetMapping(value = "/articles")
    public String[] getArticles(
            @RegisteredOAuth2AuthorizedClient("articles-client-authorization-code") OAuth2AuthorizedClient authorizedClient
    ) {

        return (String[]) Stream.of("dsgssfffffffff","dgbfsbs").toArray();
    }

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
